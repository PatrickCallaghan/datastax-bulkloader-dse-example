package com.datastax.sampledata;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
//	create table transactions (
//		accid text,
//		txtnid uuid,
//		txtntime timuuid,
//		amount double,
//		type int,
//		reason text
//		PRIMARY KEY((accid,month), txtntime)
//	)
			
	private String txtnId;
	private String acountId;
	private double amount;
	private Date txtnDate;
	private String reason;
	private String type;
	
	public String getTxtnId() {
		return txtnId;
	}
	public void setTxtnId(String txtnId) {
		this.txtnId = txtnId;
	}
	public String getAcountId() {
		return acountId;
	}
	public void setAcountId(String acountId) {
		this.acountId = acountId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getTxtnDate() {
		return txtnDate;
	}
	public void setTxtnDate(Date txtnDate) {
		this.txtnDate = txtnDate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Transaction [txtnId=" + txtnId + ", acountId=" + acountId
				+ ", amount=" + amount + ", txtnDate=" + txtnDate + ", reason="
				+ reason + ", type=" + type + "]";
	}
	public String toCVSString() {
		return txtnId + "," + dateFormat.format(txtnDate)+ "," + acountId + "," + amount + "," + type + "," + reason;				
	}
}
