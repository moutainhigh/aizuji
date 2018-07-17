package org.gz.liquidation.common.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.gz.liquidation.common.OrderBy;
import org.gz.liquidation.common.Page;
import org.gz.liquidation.common.dto.QueryDto;


@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }) })
public class QueryInterceptor implements Interceptor {
 
	public Object intercept(Invocation invocation) throws Throwable {
		// 当前环境 MappedStatement，BoundSql，及sql取得
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		if (invocation.getArgs()[1] instanceof QueryDto) {
			QueryDto param = (QueryDto) invocation.getArgs()[1];
			BoundSql boundSql = mappedStatement.getBoundSql(param.getQueryConditions());
			String originalSql = boundSql.getSql().trim();
			Object parameterObject = boundSql.getParameterObject();
			List<OrderBy> list = param.getOrderBy();
			BoundSql newBoundSql = null;
			StringBuffer sb = new StringBuffer();
			//如果需要排序
			if(null!=list && list.size() > 0){
				sb.append(" order by");
				for(int i = 0;i < list.size();i++){
					sb.append(" ").append(list.get(i).getCloumnName()).append(" ").append(list.get(i).getOrder());
					if(i != list.size()-1){
						sb.append(",");
					}
				}
			}
			Page page = param.getPage();
			// Page对象获取，“信使”到达拦截器！
			if (page != null) {
				// Page对象存在的场合，开始分页处理
				String countSql = getCountSql(originalSql);
				Connection connection = mappedStatement.getConfiguration().getEnvironment().getDataSource()
						.getConnection();
				PreparedStatement countStmt = connection.prepareStatement(countSql);
				BoundSql countBS = copyFromBoundSql(mappedStatement, boundSql, countSql);
				DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject,
						countBS);
				parameterHandler.setParameters(countStmt);
				ResultSet rs = countStmt.executeQuery();
				int totpage = 0;
				if (rs.next()) {
					totpage = rs.getInt(1);
				}
				rs.close();
				countStmt.close();
				connection.close();

				// 分页计算
				page.setTotalNum(totpage);
				// 对原始Sql追加limit
				int pageSize = page.getPageSize();
				sb.append(" limit ").append( (page.getStart()-1) * pageSize).append(",").append(pageSize);
			}
			if(sb.length() > 1){
				sb.insert(0, originalSql);
				newBoundSql = copyFromBoundSql(mappedStatement, boundSql, sb.toString());
				MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
				invocation.getArgs()[0] = newMs;
			}
			invocation.getArgs()[1] = param.getQueryConditions();
		}
		return invocation.proceed();
	}


	/**
	 * 复制MappedStatement对象
	 */
	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		String[] s = ms.getKeyProperties();
		if (s == null) {
			builder.keyProperty(null);
		} else {
			builder.keyProperty(s[0]);
		}
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());

		return builder.build();
	}

	/**
	 * 复制BoundSql对象
	 */
	private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(),
				boundSql.getParameterObject());
		for (ParameterMapping mapping : boundSql.getParameterMappings()) {
			String prop = mapping.getProperty();
			if (boundSql.hasAdditionalParameter(prop)) {
				newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
			}
		}
		return newBoundSql;
	}

	/**
	 * 根据原Sql语句获取对应的查询总记录数的Sql语句
	 */
	private String getCountSql(String sql) {
		return "SELECT COUNT(1) FROM (" + sql + ") aliasForPage";
	}

	public class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties arg0) {
		System.out.println(arg0);
	}
}