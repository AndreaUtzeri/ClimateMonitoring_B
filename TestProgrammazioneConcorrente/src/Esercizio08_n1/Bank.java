package Esercizio08_n1;

import java.util.Hashtable;

public class Bank {

	Hashtable<Integer, Integer> ccAmounts;
	//int ccAmounts[]={0,0,0};
	Bank(int size){	
		ccAmounts = new Hashtable<Integer, Integer>(size);
		for(Integer i=0; i<size; i++) {
			ccAmounts.put(i, 0);
		}
	}
	public Result executeOperation(OperationRequest op) {
		Integer ccNum= (op.getCCnumber());
		//int ccNum=op.getCCnumber();
		String opType=op.getRequest();
		// System.out.println("bank: operation "+opType+" requested for cc "+ccNum);
		Integer curValue=ccAmounts.get(ccNum);
		if(curValue==null) {
			return new Result(-1, 0, opType, false);
		}
		if(opType.equals("Deposit")) {
			ccAmounts.put(ccNum, curValue+op.getAmount());
			return new Result(ccNum, curValue+op.getAmount(), opType, true);
		} else if (opType.equals("Withdraw")) {
			if(op.getAmount()>curValue) {
				return new Result(ccNum, 0, opType, false);
			} else {
				ccAmounts.put(ccNum, curValue-op.getAmount());
				return new Result(ccNum, curValue-op.getAmount(), opType, true);
			}
		} else {
			return new Result(ccNum, 0, "unknown", false);
		}
	}
/*
	public Result executeOperation(OperationRequest op) {
		int ccNum=op.getCCnumber();
		String opType=op.getRequest();
		// System.out.println("bank: operation "+opType+" requested for cc "+ccNum);
		if(ccNum<0 || ccNum>2) {
			return new Result(-1, 0, opType, false);
		}
		if(opType.equals("Deposit")) {
			ccAmounts[ccNum]+=op.getAmount();
			return new Result(ccNum, ccAmounts[ccNum], opType, true);
		} else if (opType.equals("Withdraw")) {
			if(op.getAmount()>ccAmounts[ccNum]) {
				return new Result(ccNum, 0, opType, false);
			} else {
				ccAmounts[ccNum]-=op.getAmount();
				return new Result(ccNum, ccAmounts[ccNum], opType, true);
			}
		} else {
			return new Result(ccNum, 0, "unknown", false);
		}
	}
 */
}

