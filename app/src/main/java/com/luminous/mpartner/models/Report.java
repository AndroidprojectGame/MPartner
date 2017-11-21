package com.luminous.mpartner.models;

public class Report {

    public String Scheme_Name, dealer_code,
            Dealer_Name, BattRec, BattAcp, HkvaRec,
            HkvaAcp, InvRec, InvAcp, TotalRec, TotalAcp,
            TotalRej, DlrPay, DistPay, Total_Payout;

    public Report(String scheme_Name, String dealer_code, String dealer_Name,
                  String battRec, String battAcp, String hkvaRec, String hkvaAcp,
                  String invRec, String invAcp, String totalRec, String totalAcp,
                  String totalRej, String dlrPay, String distPay, String total_Payout) {
        Scheme_Name = scheme_Name;
        this.dealer_code = dealer_code;
        Dealer_Name = dealer_Name;
        BattRec = battRec;
        BattAcp = battAcp;
        HkvaRec = hkvaRec;
        HkvaAcp = hkvaAcp;
        InvRec = invRec;
        InvAcp = invAcp;
        TotalRec = totalRec;
        TotalAcp = totalAcp;
        TotalRej = totalRej;
        DlrPay = dlrPay;
        DistPay = distPay;
        Total_Payout = total_Payout;
    }
}
