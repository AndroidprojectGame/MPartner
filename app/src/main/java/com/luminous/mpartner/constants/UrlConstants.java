package com.luminous.mpartner.constants;

public class UrlConstants {

    public static final String NAMESPACE = "http://tempuri.org/";
    public static final String URL = "http://sapservice.luminousindia.com/Lumb2bWebService/service.asmx";
   // public static final String URL1 = "http://mpartner.luminousindia.com/webservices/getbanner.asmx";
  //  public static final String URL1 = "http://50.62.56.149:5051/webservices/getbanner.asmx";
    public static final String URL1 = "http://50.62.56.149:5052/webservices/getbanner.asmx";  //By Anusha
    // KEYS
    public static final String KEY_CODE = "Code";
    public static final String KEY_DESCRIPTION = "des";
    public static final String KEY_ANY = "anyType{}";
    public static final String KEY_SLASH = "-";
    public static final String KEY_BLANK = "";

    // STATUS
    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_ERROR = "ERROR";

    // CREATE OTP
    public static final String METHOD_CREATE_OTP = "sscMDisCreateOtp";

    // VERIFY OTP
    public static final String METHOD_VALIDATE_OTP = "sscMDisVarifyOtp";

    // DEALER LIST
    public static final String METHOD_DEALER_LIST = "sscDealerList";
    public static final String KEY_DEALERCODE = "DlrCode";
    public static final String KEY_DEALERNAME = "DlrName";

    // DEALER DETAIL
    public static final String METHOD_DEALER_ADDRESS = "sscDealerNameAddress";
    public static final String KEY_DEALERADDRESS = "DlrAddress";

    // ACCESS CODES
    public static final String METHOD_ACCESS_CODE = "sscDispatchableStockListWithAccessCode";
    public static final String KEY_SKUMODELID = "SKUModelId";
    public static final String KEY_ACCESSCODE = "AccessCode";
    public static final String KEY_SKUMODELNAME = "SKUModelName";
    public static final String KEY_QUANTITY = "TAvlQuantity";

    // SUBMIT ORDER
    public static final String METHOD_SUBMIT_ORDER = "sscMInsertDispatchStockWithSerialNoModified";
    public static final String METHOD_SERIAL_EXISTANCCE = "mSerWRSrNoExistance";
    public static final String METHOD_DEALER_BY_DISTRIBUTER_LIST = "mSerWRDlrListByDist";
    public static final String METHOD_DISTRIBUTER_BY_DEALER_LIST = "mSerWRDistListByDlr";
    public static final String METHOD_STATE_LIST = "SerWRStateList";
    public static final String METHOD_CITY_LIST = "SerWRGetCityName";
    public static final String METHOD_SAVE_ENTRY = "mSerWRSaveEntryUpdatedScheme";
    public static final String METHOD_REPORTS = "mSerSchemeWisePayout";
    public static final String METHOD_GET_PROFILE = "GetProfile";
    public static final String METHOD_UPDATE_PROFILE = "UpdateProfile";
    public static final String METHOD_GET_BANNER = "GetBannerImage";
    public static final String METHOD_GET_PRODUCTS = "getProducts";
    public static final String METHOD_GET_PRODUCT_LEVEL_ONE = "GetProductLevelOne";
    public static final String METHOD_GET_PRODUCT_LEVEL_TWO = "GetProductLevelTwo";
    public static final String METHOD_GET_PRODUCT_LEVEL_THREE = "GetProductLevelThreeVersion2";  //By Anusha
 //   public static final String METHOD_GET_PRODUCT_LEVEL_FOUR = "GetProductLevelFour";
    public static final String METHOD_GET_PRODUCT_DETAIL = "GetFullProductDetailVersion2";  //By Anusha
    public static final String METHOD_GET_PROMOTION_TYPE = "GetPermotionType";
    public static final String METHOD_GET_PROMOTION_DETAIL = "GetPromotions";
    public static final String METHOD_GET_SCHEME = "SerWRGet_Scheme_Name";
    public static final String METHOD_GET_CONTACT_US= "GetContact";
    public static final String METHOD_GET_NOTIFICATIONS= "GetNotificaions";
    public static final String METHOD_GET_SEARCH= "GetProductLevelFourByNameVersion2";  //By Anusha
    public static final String METHOD_GET_FUNCTIONS= "GetFunctionName";
    public static final String METHOD_GET_SUGGESTION= "Suggestion";
    public static final String METHOD_UPDATE_DEVICE_ID= "UpdateDeviceId";   //By Anusha

    //DEALER
    public static final String KEY_DEALER_CODE = "dlr_code";
    public static final String KEY_DEALER_NAME = "dlr_name";

    public static final String KEY_STATE_ID = "StateId";
    public static final String KEY_STATE_NAME = "StateName";
    public static final String KEY_DIST_ID = "DistId";
    public static final String KEY_DIST_NAME = "DistName";
    public static final String KEY_SCHEME_VALUE = "scheme_name";
    public static final String KEY_SCHEME_NAME = "scheme_value";


}
