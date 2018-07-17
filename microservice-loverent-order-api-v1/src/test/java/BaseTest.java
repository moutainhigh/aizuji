/*import org.gz.order.OrderApiApplication;
import org.gz.order.api.service.IRentRecordService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = OrderApiApplication.class)
@WebAppConfiguration*/
public class BaseTest {
  /*
    @Autowired
    private IRentRecordService recordService;
  
   @Test
  public void testOrderDetail() {
  ResponseResult<OrderDetailResp> orderDetail = recordService.queryOrderDetail("1");
  System.out.println(JsonUtils.toJsonString(orderDetail));
  }
  
  @Test
  public void testAddOrder()
  {
  // http://192.168.29.1:8003/api/order/addOrder
  // {"userId":"2","channelNo":"ios","productNo":"fp00000026"}
  AddOrderReq addOrderRequest = new AddOrderReq();
  addOrderRequest.setApplyTime(new Date());
  addOrderRequest.setCreateOn(new Date());
  addOrderRequest.setUserId(1L);
  addOrderRequest.setChannelNo("ios");
  addOrderRequest.setProductNo("fp00000029");
  ResponseResult<String> add = recordService.add(addOrderRequest);
  System.out.println(JsonUtils.toJsonString(add));
  }
  
  
  @Test
  public void testQueryOrderStateList() {
  OrderDetailReq orderDetailReq = new OrderDetailReq();
  orderDetailReq.setState(1);
  orderDetailReq.setUserId("1");
  ResponseResult<List<OrderDetailResp>> orderDetail = recordService.queryOrderStateList(orderDetailReq);
  System.out.println(JsonUtils.toJsonString(orderDetail));
  }
  
    @Test
  public void testSubmitOrder() {
    // http://192.168.29.1:8003/api/order/submitOrder
    // {"id":null,"userId":1,"rentRecordNo":"SO1801280000000056","productId":null,"productNo":null,"materielClassId":null,"materielClassName":null,"materielBrandId":null,"materielBrandName":null,"materielModelId":null,"materielModelName":null,"materielSpecName":null,"leaseTerm":null,"leaseAmount":null,"premium":null,"floatAmount":null,"signContractAmount":null,"sesameCredit":null,"materielNo":null,"matreielName":null,"thumbnail":null,"phoneNum":"15899663322","realName":"张三","idNo":"436559988663322","prov":"广东","city":"深圳","area":"宝安","address":"西乡八栋一楼","emergencyContact":"李四","emergencyContactPhone":"13566993311","relationship":1,"userHistory":{"id":null,"userId":null,"rentRecordNo":null,"phoneNum":null,"loginPassword":null,"gender":null,"channelType":null,"deviceId":null,"deviceType":null,"osType":null,"appVersion":null,"sourceType":null,"avatar":null,"nickName":null,"realName":null,"idNo":null,"zhimaScore":null,"cardFaceUrl":"www.baidu","cardBackUrl":"www.jlll.com","realnameCertState":1,"residenceAddress":"户籍地址","issuingAuthority":"签发机关","effectiveStartDate":1517125142843,"effectiveEndDate":1517125142843,"authAlipayUserId":null,"authWeixinOpenId":null,"userStatus":null,"birthday":null,"age":null,"job":null,"education":0,"industry":null,"monthIncome":null,"phoneUserTime":null,"userEmail":null,"companyName":null,"entryTime":null,"position":null,"companyContactNumber":null,"companyAddress":null,"schoolName":null,"livingExpenses":null,"schoolAddress":null,"hasLoanRecord":null,"loanAmount":null,"monthRepayment":null,"marital":null,"createTime":null,"updateTime":null},"createMan":"zhangsan","lng":"536256.33","lat":"45464646.22","phoneModel":"xiaomi 2S","imei":"23213"}
    SubmitOrderReq submitOrderReq = new SubmitOrderReq();
    submitOrderReq.setUserId(1L);
    submitOrderReq.setCreateMan("zhangsan");
    submitOrderReq.setLat("45464646.22");
    submitOrderReq.setLng("536256.33");
    submitOrderReq.setRentRecordNo("SO1801280000000056");
    submitOrderReq.setPhoneNum("15899663322");
    submitOrderReq.setRealName("张三");
    submitOrderReq.setIdNo("436559988663322");
    submitOrderReq.setProv("广东");
    submitOrderReq.setCity("深圳");
    submitOrderReq.setArea("宝安");
    submitOrderReq.setAddress("西乡八栋一楼");
    submitOrderReq.setEmergencyContact("李四");
    submitOrderReq.setEmergencyContactPhone("13566993311");
    submitOrderReq.setRelationship(1);
  
    UserHistory userHistory = new UserHistory();
    userHistory.setUserStatus(1);
    userHistory.setPhoneNum("15899663322");
    userHistory.setCardFaceUrl("www.baidu");
    userHistory.setCardBackUrl("www.jlll.com");
    userHistory.setRealnameCertState(1);
    userHistory.setResidenceAddress("户籍地址");
    userHistory.setIssuingAuthority("签发机关");
    userHistory.setEffectiveStartDate(new Date());
    userHistory.setEffectiveEndDate(new Date());
    userHistory.setEducation(0);
    submitOrderReq.setUserHistory(userHistory);
    System.out.println("请求参数" + JsonUtils.toJsonString(submitOrderReq));
    ResponseResult<OrderDetailResp> result = recordService.submitOrder(submitOrderReq);
    System.out.println(JsonUtils.toJsonString(result));
  }
  
  @Test
  public void updateOrderState()
  {
  // http://192.168.29.1:8003/api/order/updateOrderState
  //审批通过
  // {"rentRecordNo":"SO1712220000000005","state":3,"createBy":22,"createMan":"shenpirenyuan","remark":"1111","lng":"","lat":"","sealAgreementUrl":"","evid":""}
  //支付完成
  {"rentRecordNo":"SO1801070000000018","state":4,"createBy":3,"createMan":"lisi","remark":"1111","lng":"","lat":"","sealAgreementUrl":"","evid":""}
  //签约完成（待检货）
  //{"rentRecordNo":"SO1712220000000005","state":7,"createBy":1,"createMan":"zhangsan","remark":"签约完成","lng":"","lat":"","sealAgreementUrl":"pdf/agreement/temp/SO1712220000000005.pdf","evid":"123123","signServiceId":"88888"}
  //待发货
  //{"rentRecordNo":"SO1712220000000005","state":8,"createBy":3,"createMan":"cangguanyuan","remark":"待发货","lng":"","lat":"","sealAgreementUrl":"","evid":""}
  //签收
  //{"rentRecordNo":"SO1712220000000005","state":10,"createBy":1,"createMan":"zhangsan","remark":"签收","lng":"","lat":"","sealAgreementUrl":"","evid":""}
  UpdateOrderStateReq submitOrderReq = new UpdateOrderStateReq();
  submitOrderReq.setCreateBy(22L);;
  submitOrderReq.setCreateMan("shenpirenyuan");
  submitOrderReq.setLat("");
  submitOrderReq.setLng("");
  submitOrderReq.setRentRecordNo("SO1712220000000005");
  submitOrderReq.setEvid("");
  submitOrderReq.setRemark("1111");
  submitOrderReq.setSealAgreementUrl("");
  submitOrderReq.setState(3);
  System.out.println("请求参数" + JsonUtils.toJsonString(submitOrderReq));
  ResponseResult<String> result = recordService.updateOrderState(submitOrderReq);
  System.out.println(JsonUtils.toJsonString(result));
  }
  
  @Test
  public void applySign()
  {
  // http://192.168.29.1:8003/api/order/applySign
  // {"rentRecordNo":"SO1712220000000005","state":"","createBy":1,"createMan":"zhangsan","remark":"2222","lng":"45654654.66","lat":"999.66","sealAgreementUrl":"","evid":"","imei":"123","phoneModel":"xiaomi 1S"}
  UpdateOrderStateReq submitOrderReq = new UpdateOrderStateReq();
  submitOrderReq.setCreateBy(1L);;
  submitOrderReq.setCreateMan("zhangsan");
  submitOrderReq.setLat("45654654.66");
  submitOrderReq.setLng("999.66");
  submitOrderReq.setRentRecordNo("SO1712220000000005");
  submitOrderReq.setEvid("");
  submitOrderReq.setRemark("2222");
  submitOrderReq.setSealAgreementUrl("");
  System.out.println("请求参数" + JsonUtils.toJsonString(submitOrderReq));
  ResponseResult<String> result;
  try
   {
     result = recordService.applySign(submitOrderReq);
     System.out.println(JsonUtils.toJsonString(result));
   } catch (Exception e)
   {
     e.printStackTrace();
   }
  
  }
  
  
  @Test
  public void SendOut()
  {
   // http://192.168.29.1:8003/api/order/SendOut
   // {"rentRecordNo":"SO1712220000000005","state":"9","createBy":9,"createMan":"cangguanyuan","logisticsNo":"2132145656456"}
   RentRecordReq rentRecordReq = new RentRecordReq();
   rentRecordReq.setState(9);
   rentRecordReq.setCreateBy(3L);
   rentRecordReq.setCreateMan("cangguanyuan");
   rentRecordReq.setLogisticsNo("2132145656456");
   // rentRecordReq.setReturnLogisticsNo("7892222222");
   rentRecordReq.setRentRecordNo("SO1712220000000005");
  
   System.out.println("请求参数" + JsonUtils.toJsonString(rentRecordReq));
   ResponseResult<String> result;
   try
     {
       result = recordService.SendOut(rentRecordReq);
       System.out.println(JsonUtils.toJsonString(result));
     } catch (Exception e)
     {
       e.printStackTrace();
     }
  
  }
  
  
  @Test
  public void queryPageRentRecordList()
   {
     // http://192.168.29.1:8003/api/order/queryPageRentRecordList
     // {"currPage":1,"pageSize":10,"startIndex":0,"endIndex":10,"rentRecordNo":"","snCode":"","imei":"","signStartTime":"","signEndTime":"","state":5,"orderBy":null,"sortId":null}
     RentRecordQuery rentRecordQuery = new RentRecordQuery();
     rentRecordQuery.setState(5);
     rentRecordQuery.setRentRecordNo("");
     rentRecordQuery.setSignStartTime("");
     rentRecordQuery.setSignEndTime("");
     rentRecordQuery.setSnCode("");
     rentRecordQuery.setImei("");
     System.out.println("请求参数" + JsonUtils.toJsonString(rentRecordQuery));
     ResponseResult<ResultPager<RentRecord>> result = recordService.queryPageRentRecord(rentRecordQuery);
     System.out.println(JsonUtils.toJsonString(result));
   }
   
    @Test
  public void sureSign()
  {
  // http://192.168.29.1:8003/api/order/sureSign
  // {"rentRecordNo":"SO1712220000000005","state":"","createBy":1,"createMan":"zhangsan","remark":"2222","lng":"","lat":"","sealAgreementUrl":"https://osstest-01.oss-cn-beijing.aliyuncs.com/pdf/agreement/temp/3SO1801070000000018.pdf","evid":"1111","signServiceId":"5555"}
  UpdateOrderStateReq submitOrderReq = new UpdateOrderStateReq();
  submitOrderReq.setCreateBy(1L);
  submitOrderReq.setCreateMan("zhangsan");
  submitOrderReq.setRentRecordNo("SO1712220000000005");
  submitOrderReq.setRemark("2222");
  submitOrderReq.setEvid("111");
   submitOrderReq.setSignServiceId("3233");
  submitOrderReq.setSealAgreementUrl("https://osstest-01.oss-cn-beijing.aliyuncs.com/pdf/agreement/temp/3SO1801070000000018.pdf");
  System.out.println("请求参数" + JsonUtils.toJsonString(submitOrderReq));
  ResponseResult<String> result;
  try
   {
     result = recordService.sureSign(submitOrderReq);
     System.out.println(JsonUtils.toJsonString(result));
   } catch (Exception e)
   {
     e.printStackTrace();
   }
  
  }
  
  // http://192.168.29.1:8003/api/order/queryPageWokerOrderList
  {"rentRecordNo":"SO1712300000000022","idNo":"","phoneNum":"17688717987"} 
  
  
    @Test
  public void queryOrderList() {
    RentRecordQuery rentRecordQuery = new RentRecordQuery();
    OrderBy orderBy = new OrderBy();
    orderBy.setCloumnName("rr.id");
    orderBy.setSequence("desc");
    List<OrderBy> orderBylist = new ArrayList<>();
    orderBylist.add(orderBy);
    rentRecordQuery.setOrderBy(orderBylist);
    rentRecordQuery.setNotZero(0);
    rentRecordQuery.setUserId(22L);
    ResponseResult<List<OrderDetailResp>> result = new ResponseResult<>();
    System.out.println("请求参数===================" + JsonUtils.toJsonString(rentRecordQuery));
    result = recordService.queryOrderList(rentRecordQuery);
    System.out.println("响应结果===================" + JsonUtils.toJsonString(result));
  }
  
     */

}
