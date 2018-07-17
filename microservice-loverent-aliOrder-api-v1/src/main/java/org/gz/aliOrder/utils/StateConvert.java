package org.gz.aliOrder.utils;

import java.util.ArrayList;
import java.util.List;

import org.gz.aliOrder.Enum.BackRentState;
import org.gz.aliOrder.Enum.FrontALiState;
import org.gz.aliOrder.Enum.FrontRentState;

/**
 * 订单/租赁 前后端状态转换
 * @author phd
 */
public class StateConvert {
	
	/**
	 * 据后台租借状态获取对应的前端租借状态
	 * @param backRentStateCode
	 * @return
	 */
	public static FrontRentState getFrontRentStateByBackRentState(int backRentStateCode){
		switch (backRentStateCode) {
		case 0:
			return FrontRentState.ApplyPending;
		case 1:
			return FrontRentState.ApprovalPending;
		case 2:
			return FrontRentState.Refuse;
		case 3:
			return FrontRentState.WaitPayment;
		case 4:
      case 6:
			return FrontRentState.WaitSignup;
		case 5:
			return FrontRentState.Cancel;
		case 7:
		case 8:
			return FrontRentState.WaitSend;
		case 9:
			return FrontRentState.SendOut;
		case 10:
        return FrontRentState.Renting;
      case 24:
        return FrontRentState.EndPerformance;
      case 15:
        return FrontRentState.Overdue;
      case 28:
        return FrontRentState.WAITSETTLE;
      case 16:
      case 23:
        return FrontRentState.Returning;
      case 18:
		case 20:
      case 32:
			return FrontRentState.Return;
      case 17:
		case 19:
      case 26:
			return FrontRentState.BuyOut;
        case 22:
      case 21:
        	return FrontRentState.BuyIng;
      case 29:
        return FrontRentState.Expires;
      case 25:
        return FrontRentState.BuyOutNotice;
      case 30:
        return FrontRentState.ReturnNotice;
      case 27:
      case 31:
        return FrontRentState.DamageCheck;
		default:
			return null;
		}
	}

  /**
   * 据后台租借状态获取对应的前端支付宝小程序订单状态
   * 
   * @param backRentStateCode
   * @return
   */
  public static FrontALiState getFrontAliStateByBackRentState(int backRentStateCode) {
    switch (backRentStateCode) {
      case 0:
        return FrontALiState.ApplyPending;
      case 3:
        return FrontALiState.WaitPayment;
      case 5:
        return FrontALiState.Cancel;
      case 7:
      case 8:
        return FrontALiState.WaitSend;
      case 9:
        return FrontALiState.SendOut;
      case 24:
      case 10:
        return FrontALiState.Renting;
      case 17:
      case 19:
      case 26:
        return FrontALiState.BuyOut;
      case 35:
        return FrontALiState.OrderClose;
      default:
        return null;
    }
  }
	
	/**
	 * 据前端租借状态获取对应的后端租借状态
	 * @param frontRentStateCode
	 */
	public static List<Integer> getBackRentStateByFrontRentState(int frontRentStateCode){
		List<Integer> states=new ArrayList<>();
		switch (frontRentStateCode) {
		case 0:
			states.add(BackRentState.ApplyPending.getCode());
			break;
		case 1:
			states.add(BackRentState.ApprovalPending.getCode());
			break;
		case 2:
			states.add(BackRentState.Refuse.getCode());
			break;
		case 3:
			states.add(BackRentState.WaitPayment.getCode());
			break;
		case 4:
			states.add(BackRentState.WaitSignup.getCode());
			states.add(BackRentState.Change.getCode());
			break;
		case 5:
			states.add(BackRentState.Cancel.getCode());
			break;
		case 6:
			states.add(BackRentState.WaitAssembly.getCode());
			states.add(BackRentState.WaitPick.getCode());
			states.add(BackRentState.WaitSend.getCode());
			break;
		case 7:
			states.add(BackRentState.SendOut.getCode());
			break;
		case 8:
			states.add(BackRentState.NormalPerformance.getCode());
			states.add(BackRentState.EarlyBuyIng.getCode());
			states.add(BackRentState.Repair.getCode());
			break;
		case 9:
			break;
		case 10:
			states.add(BackRentState.Overdue.getCode());
			break;
		case 11:
			states.add(BackRentState.Recycling.getCode());
			break;
		case 12:
			states.add(BackRentState.PrematureTermination.getCode());
			states.add(BackRentState.EarlyRecycle.getCode());
			states.add(BackRentState.Recycle.getCode());
			break;
		case 13:
			states.add(BackRentState.EarlyBuyout.getCode());
			states.add(BackRentState.NormalBuyout.getCode());
			break;
		case 14:
			states.add(BackRentState.EarlyRecycing.getCode());
			states.add(BackRentState.PrematureTerminating.getCode());
			break;
		case 15:
			states.add(BackRentState.NormalBuyIng.getCode());
			break;
		default:
			break;
		}
		return states;
	}
	
	/**
	 * 据前端订单状态获取对应的后端租借状态
	 * @param frontOrderStateCode
	 */
	public static List<Integer> getBackRentStateByFrontOrderState(int frontOrderStateCode){
		List<Integer> states=new ArrayList<>();
		switch (frontOrderStateCode) {
		case 1:
			states.add(BackRentState.ApplyPending.getCode());
			states.add(BackRentState.ApprovalPending.getCode());
			states.add(BackRentState.WaitPayment.getCode());
			states.add(BackRentState.WaitSignup.getCode());
			states.add(BackRentState.Change.getCode());
			states.add(BackRentState.WaitAssembly.getCode());
			states.add(BackRentState.WaitPick.getCode());
			states.add(BackRentState.WaitSend.getCode());
			states.add(BackRentState.SendOut.getCode());
			states.add(BackRentState.NormalPerformance.getCode());
			states.add(BackRentState.EarlyBuyIng.getCode());
			states.add(BackRentState.Repair.getCode());
			states.add(BackRentState.Overdue.getCode());
			states.add(BackRentState.Recycling.getCode());
			states.add(BackRentState.EarlyRecycing.getCode());
			states.add(BackRentState.PrematureTerminating.getCode());
        states.add(BackRentState.EndPerformance.getCode());
        states.add(BackRentState.ForceBuyIng.getCode());
        states.add(BackRentState.DamageCheck.getCode());
        states.add(BackRentState.ForcePerformanceIng.getCode());
        states.add(BackRentState.ForcePerformanceEnd.getCode());
        states.add(BackRentState.ForceRecycleIng.getCode());
        states.add(BackRentState.ForceDamageCheck.getCode());
        states.add(BackRentState.ReturnGoodIng.getCode());

			break;
		case 2:
			states.add(BackRentState.PrematureTermination.getCode());
			states.add(BackRentState.EarlyRecycle.getCode());
			states.add(BackRentState.Recycle.getCode());
			states.add(BackRentState.EarlyBuyout.getCode());
			states.add(BackRentState.NormalBuyout.getCode());
        states.add(BackRentState.ForceBuyout.getCode());
        states.add(BackRentState.ForceRecycleEnd.getCode());
        states.add(BackRentState.ReturnGoodEnd.getCode());
			break;
		case 3:
			states.add(BackRentState.Refuse.getCode());
			states.add(BackRentState.Cancel.getCode());
			break;
		default:
			break;
		}
		return states;
	}

  /**
   * 检查传入的订单状态是否符合当前订单中订单状态的流转
   * 
   * @param reqState 请求更新订单状态
   * @param orderState 当前订单中的订单状态
   * @return
   * @throws createBy:临时工 createDate:2018年1月10日
   */
  public static boolean checkOrderState(int reqState, int orderState) {
    // 如果 请求更新订单状态为 1 待审批 则当前订单中的订单状态需要 为 0提交
    if (BackRentState.ApprovalPending.getCode() == reqState) {
      if (orderState != BackRentState.ApplyPending.getCode()) {
        return false;
      }
    }

    // 如果 请求更新订单状态为 2 已拒绝 则当前订单中的订单状态需要 为 {1 待审批 }
    if (BackRentState.Refuse.getCode() == reqState) {
      if (orderState != BackRentState.ApprovalPending.getCode()) {
        return false;
      }
    }
    // 如果 请求更新订单状态为 3 待支付 则当前订单中的订单状态需要 为 {1 待审批 }
    if (BackRentState.WaitPayment.getCode() == reqState) {
      if (orderState != BackRentState.ApprovalPending.getCode()) {
        return false;
      }
    }

    // 如果 请求更新订单状态为 4 待签约 则当前订单中的订单状态需要 为 {3 待支付 }
    if (BackRentState.WaitSignup.getCode() == reqState) {
      if (orderState != BackRentState.WaitPayment.getCode()) {
        return false;
      }
    }
    // 如果 请求更新订单状态为 5 已取消 则当前订单中的订单状态需要 为 {0申请中 3 待支付 4 待签约 6 待配货}
    if (BackRentState.Cancel.getCode() == reqState) {
      if (orderState != BackRentState.WaitPayment.getCode()
          && orderState != BackRentState.WaitSignup.getCode()
          && orderState != BackRentState.WaitAssembly.getCode()
          && orderState != BackRentState.ApplyPending.getCode()) {
        return false;
      }
    }
    // 如果 请求更新订单状态为 6 待配货 则当前订单中的订单状态需要 为 { 4 待签约 }
    if (BackRentState.WaitAssembly.getCode() == reqState) {
      if (orderState != BackRentState.WaitSignup.getCode()) {
        return false;
      }
    }
    // 如果 请求更新订单状态为 7 待拣货 则当前订单中的订单状态需要 为 { 4 待签约 ， 6 待配货 ，8 待发货}
    if (BackRentState.WaitPick.getCode() == reqState) {
      if (orderState != BackRentState.WaitSignup.getCode()
          && orderState != BackRentState.WaitAssembly.getCode()
          && orderState != BackRentState.WaitSend.getCode()
      ) {
        return false;
      }
    }

    // 如果 请求更新订单状态为 8 待发货 则当前订单中的订单状态需要 为 { 7 待拣货 }
    if (BackRentState.WaitSend.getCode() == reqState) {
      if (orderState != BackRentState.WaitPick.getCode()) {
        return false;
      }
    }
    // 如果 请求更新订单状态为 9 发货中 则当前订单中的订单状态需要 为 { 8 待发货 }
    if (BackRentState.SendOut.getCode() == reqState) {
      if (orderState != BackRentState.WaitSend.getCode()) {
        return false;
      }
    }
    // 如果 请求更新订单状态为 10 正常履约（已经签收） 则当前订单中的订单状态需要 为 { 9 发货中 15 已逾期}
    if (BackRentState.NormalPerformance.getCode() == reqState) {
      if (orderState != BackRentState.SendOut.getCode() && orderState != BackRentState.Overdue.getCode()) {
        return false;
      }
    }

    // 如果 请求更新订单状态为 21提前买断中 则当前订单中的订单状态需要 为 { 10 正常履约（已经签收） 21提前买断中 }
    // if (BackRentState.EarlyBuyIng.getCode() == reqState) {
    // if (orderState != BackRentState.NormalPerformance.getCode()
    // && orderState != BackRentState.EarlyBuyIng.getCode()) {
    // return false;
    // }
    // }
    // 如果 请求更新订单状态为 17 提前买断 则当前订单中的订单状态需要 为 { 10 正常履约（已经签收） }
    if (BackRentState.EarlyBuyout.getCode() == reqState) {
      if (orderState != BackRentState.NormalPerformance.getCode()) {
        return false;
      }
    }

    // 如果 请求更新订单状态为 24履约完成 则当前订单中的订单状态需要 为 { 10 正常履约（已经签收） 15 已逾期 }
    if (BackRentState.EndPerformance.getCode() == reqState) {
      if (orderState != BackRentState.NormalPerformance.getCode()
          && orderState != BackRentState.Overdue.getCode()) {
        return false;
      }
    }

    // TODO 续租中 已续租 还没设计

    // 如果 请求更新订单状态为 22正常买断中 则当前订单中的订单状态需要 为 { 24履约完成 22正常买断中 }
    // if (BackRentState.NormalBuyIng.getCode() == reqState) {
    // if (orderState != BackRentState.EndPerformance.getCode() && orderState !=
    // BackRentState.NormalBuyIng.getCode()) {
    // return false;
    // }
    // }
    // 如果 请求更新订单状态为 19 正常买断 则当前订单中的订单状态需要 为 { 24履约完成 , 10 正常履约}
    if (BackRentState.NormalBuyout.getCode() == reqState) {
      if (orderState != BackRentState.EndPerformance.getCode()
          && orderState != BackRentState.NormalPerformance.getCode()) {
        return false;
      }
    }
    // 如果 请求更新订单状态为 16 归还中 则当前订单中的订单状态需要 为 { 24履约完成 }
    if (BackRentState.Recycling.getCode() == reqState) {
      if (orderState != BackRentState.EndPerformance.getCode()) {
        return false;
      }
    }
    // 如果 请求更新订单状态为 27定损完成 则当前订单中的订单状态需要 为 { 16 归还中 }
    if (BackRentState.DamageCheck.getCode() == reqState) {
      if (orderState != BackRentState.Recycling.getCode()) {
        return false;
      }
    }
    // 如果 请求更新订单状态为 20 已归还 则当前订单中的订单状态需要 为 { 27定损完成 }
    if (BackRentState.Recycle.getCode() == reqState) {
      if (orderState != BackRentState.DamageCheck.getCode()) {
        return false;
      }
    }

    // 如果 请求更新订单状态为 15 已逾期 则当前订单中的订单状态需要 为 { 10 正常履约（已经签收） }
    if (BackRentState.Overdue.getCode() == reqState) {
      if (orderState != BackRentState.NormalPerformance.getCode()) {
        return false;
      }
    }
    // 如果 请求更新订单状态为 25 强制买断中 则当前订单中的订单状态需要 为 { 15 已逾期 25 强制买断中}
    if (BackRentState.ForceBuyIng.getCode() == reqState) {
      if (orderState != BackRentState.Overdue.getCode() && orderState != BackRentState.ForceBuyIng.getCode()) {
        return false;
      }
    }
    // 如果 请求更新订单状态为 26 强制买断 则当前订单中的订单状态需要 为 {25 强制买断中 }
    if (BackRentState.ForceBuyout.getCode() == reqState) {
      if (orderState != BackRentState.ForceBuyIng.getCode()) {
        return false;
      }
    }
    // 如果 请求更新订单状态为 28 强制履约中 则当前订单中的订单状态需要 为 { 15 已逾期 28 强制履约中}
    if (BackRentState.ForcePerformanceIng.getCode() == reqState) {
      if (orderState != BackRentState.Overdue.getCode() && orderState != BackRentState.ForcePerformanceIng.getCode()) {
        return false;
      }
    }
    // 如果 请求更新订单状态为 29 强制履约完成 则当前订单中的订单状态需要 为 { 28 强制履约中 }
    if (BackRentState.ForcePerformanceEnd.getCode() == reqState) {
      if (orderState != BackRentState.ForcePerformanceIng.getCode()) {
        return false;
      }
    }
    // 如果 请求更新订单状态为 30 强制归还中 则当前订单中的订单状态需要 为 { 29 强制履约完成 }
    if (BackRentState.ForceRecycleIng.getCode() == reqState) {
      if (orderState != BackRentState.ForcePerformanceEnd.getCode()) {
        return false;
      }
    }
    // 如果 请求更新订单状态为 31 强制定损完成 则当前订单中的订单状态需要 为 { 30 强制归还中 }
    if (BackRentState.ForceDamageCheck.getCode() == reqState) {
      if (orderState != BackRentState.ForceRecycleIng.getCode()) {
        return false;
      }
    }
    // 如果 请求更新订单状态为 32 强制归还完成 则当前订单中的订单状态需要 为 { 31 强制定损完成 }
    if (BackRentState.ForceRecycleEnd.getCode() == reqState) {
      if (orderState != BackRentState.ForceDamageCheck.getCode()) {
        return false;
      }
    }

    return true;
  }

  /**
   * 检查传入的小程序订单状态是否符合当前订单中订单状态的流转
   * 
   * @param reqState 请求更新订单状态
   * @param orderState 当前订单中的订单状态
   * @return
   * @throws createBy:临时工 createDate:2018年2月09日
   */
  public static boolean checkAliOrderState(int reqState, int orderState) {

    // 如果 请求更新订单状态为 7 待拣货 则当前订单中的订单状态需要 为 { 3 待支付}
    if (BackRentState.WaitPick.getCode() == reqState) {
      if (orderState != BackRentState.WaitPayment.getCode()) {
        return false;
      }
    }

    // 如果 请求更新订单状态为 8 待发货 则当前订单中的订单状态需要 为 { 7 待拣货 }
    if (BackRentState.WaitSend.getCode() == reqState) {
      if (orderState != BackRentState.WaitPick.getCode()) {
        return false;
      }
    }
    // 如果 请求更新订单状态为 9 发货中 则当前订单中的订单状态需要 为 { 8 待发货 }
    if (BackRentState.SendOut.getCode() == reqState) {
      if (orderState != BackRentState.WaitSend.getCode()) {
        return false;
      }
    }
    // 如果 请求更新订单状态为 10 正常履约（已经签收） 则当前订单中的订单状态需要 为 { 9 发货中 15 已逾期}
    if (BackRentState.NormalPerformance.getCode() == reqState) {
      if (orderState != BackRentState.SendOut.getCode() && orderState != BackRentState.Overdue.getCode()) {
        return false;
      }
    }

    // 如果 请求更新订单状态为 17 提前买断 则当前订单中的订单状态需要 为 { 10 正常履约（已经签收） }
    if (BackRentState.EarlyBuyout.getCode() == reqState) {
      if (orderState != BackRentState.NormalPerformance.getCode()) {
        return false;
      }
    }

    // 如果 请求更新订单状态为 24履约完成 则当前订单中的订单状态需要 为 { 10 正常履约（已经签收） 15 已逾期 }
    if (BackRentState.EndPerformance.getCode() == reqState) {
      if (orderState != BackRentState.NormalPerformance.getCode()
          && orderState != BackRentState.Overdue.getCode()) {
        return false;
      }
    }

    // 如果 请求更新订单状态为 19 正常买断 则当前订单中的订单状态需要 为 { 24履约完成 , 10 正常履约 15 已逾期}
    if (BackRentState.NormalBuyout.getCode() == reqState) {
      if (orderState != BackRentState.EndPerformance.getCode()
          && orderState != BackRentState.NormalPerformance.getCode()
          && orderState != BackRentState.Overdue.getCode()) {
        return false;
      }
    }

    return true;
  }

}
