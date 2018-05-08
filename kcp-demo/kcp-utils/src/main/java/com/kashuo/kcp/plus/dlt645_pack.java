package com.kashuo.kcp.plus;

public class dlt645_pack {
	
	//rule ID
	byte  BDi0;
	
	byte  BDi1;
	
	byte  BDi2;
	
	byte  BDi3;
	
	//contrl code
	byte CtrlCode;
	
	//�ѵ��������Լ����
	byte DataLen;
	
	//������ʵ����������4�ֽڵĹ�ԼID,������Ϊ0�ֽ�
	byte RealLen;
	
	String AddrStr;
	
	public  void GetRuleID(String strRule) {

		BDi3 = (byte)Integer.parseInt(strRule.substring(0, 2));
		BDi2 = (byte)Integer.parseInt(strRule.substring(2, 4));
		BDi1 = (byte)Integer.parseInt(strRule.substring(4, 6));
		BDi0 = (byte)Integer.parseInt(strRule.substring(6, 8));
		//System.out.println("rule ID[0]:" + Integer.parseInt(strRule.substring(0, 2)));
		//System.out.println("rule ID[1]:" + Integer.parseInt(strRule.substring(2, 4)));
		//System.out.println("rule ID[2]:" + Integer.parseInt(strRule.substring(4, 6)));
		//System.out.println("rule ID[3]:" + Integer.parseInt(strRule.substring(6, 8)));
	}

	public  void GetContrlCode(byte ctrlCode) {
		byte bD7, bD6, bD5, bD40;
		
		//ͨ����Լ��Ϣת����������
		bD7 = 0x00;
		bD6 = 0x00;
		bD5 = 0x00;
		bD40 = 0x11;
		
		ctrlCode |= bD7 << 7;
		ctrlCode |= bD6 << 6;
		ctrlCode |= bD5 << 5;
		ctrlCode |= bD40 & 0x1F;
		
		CtrlCode = ctrlCode ;
		System.out.println(CtrlCode);
		
		
	}
	
	public void GetDataLen(byte dataLen) {
		DataLen = dataLen;
	}
	
	
	public void GetRealLen(byte realLen) {
		RealLen = realLen;
	}
	
	public void GetAddrStr(String addr) {
		AddrStr = addr;
	}
	
	public static byte[] d07_str2bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;
		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}
		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}
		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;
		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}
			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}
			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}
	
	public static void pack_any_frame_by_data(dlt645_pack inPut ,byte [] bFrame) {
		
		byte bDi0,bDi1,bDi2,bDi3;
		int len = 0;
		int i;
		byte checksum = 0;
		
		//prepare for Data
		bDi0 = inPut.BDi0;
		bDi1 = inPut.BDi1;
		bDi2 = inPut.BDi2;
		bDi3 = inPut.BDi3;
		
		System.out.println("bDi0:" + bDi0 );
		System.out.println("bDi1:" + bDi1 );
		System.out.println("bDi2:" + bDi2 );
		System.out.println("bDi3:" + bDi3 );
		
		byte [] addr = d07_str2bcd(inPut.AddrStr);
		
		System.out.println("addrss length:" + addr.length);
	    //1. Frame begin
		bFrame[len++] = 0x68;
		
		//2.fill address
	    for(i = 0; i < addr.length; i++){
	    	bFrame[len++] = addr[i];
	    }
	    
	    //3.frame begin sign
	    bFrame[len++] = 0x68; 
	    
	    //4. contrl code
	    bFrame[len++] = inPut.CtrlCode;
	    
	    //5. data filed bytes
	    bFrame[len++] = inPut.DataLen;
	    
	    //6. rule id  plus 0x33
	    bFrame[len++] = (byte)(bDi0 + 0x33);
	    bFrame[len++] = (byte)(bDi1 + 0x33);
	    bFrame[len++] = (byte)(bDi2 + 0x33);
	    bFrame[len++] = (byte)(bDi3 + 0x33);
	    
	    //7. other data
	    //have handled .
	    
	    //8. cal checksum 
	    for(i = 0; i < len; i++) {
	    	checksum += bFrame[i];
	    }
	    
	  	//9. fill checksum filed
	    bFrame[len++] = checksum;
	    
	    //10. end sign
	    bFrame[len++] = 0x16;
		
	}



	public static void main (String[] args) {
		int i ;
		String strRule = "111111111111";
		String ruleID = "00010000";
		byte [] frame = new byte[255];
		
		dlt645_pack pkt = new dlt645_pack();
		
		pkt.GetRuleID(ruleID);
		pkt.GetContrlCode((byte)0);
		pkt.GetDataLen((byte)4);
		pkt.GetRealLen((byte)4);
		pkt.GetAddrStr(strRule);
		
		pkt.pack_any_frame_by_data(pkt, frame);
		
		System.out.println("Frame:");
//		StringBuilder sb = new StringBuilder();
//		for(i = 0;i < frame.length; i++) {
////			System.out.println(Integer.toHexString(frame[i]));
//			System.out.println(String.valueOf(Integer.toHexString(frame[i])));
//			if(!"0".equals(String.valueOf(Integer.toHexString(frame[i])))){
//				if(String.valueOf(Integer.toHexString(frame[i])).length() ==1){
//					sb.append("0"+String.valueOf(Integer.toHexString(frame[i])));
//				}else {
//					sb.append(String.valueOf(Integer.toHexString(frame[i])));
//				}
//			}
//		}
//		System.out.println(sb.toString());

	}
	
}
