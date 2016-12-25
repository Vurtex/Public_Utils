package com.puhua.crm.common;

import com.puhua.crm.entity.User;

/**
 * @author Vurtex 接口通讯录
 */
public class Constants {
	/**
	 * true = 接口数据，false = 假数据
	 */
	public static final boolean isDebug = true;
	/**
	 * 是否加密 true 加密 false 不加密
	 */
	public static final boolean isEncrypt = true;
	public String AppId = "";
	/** 版本信息 */
	public static final String VERSION_NAME = "Tina";
	public static final String SYSTEM_FILE = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/Crm";
	/** 线上服务器 */
	// public static final String WEB_SERVER_IP =
	// "http://192.168.62.53:7022";//"http://192.168.181.25:17011";//
	// public static final String WEB_SERVER_IP = "http://192.168.69.89:7011";
	// public static final String WEB_SERVER_IP = "";
	public static final String IMG_Upload_URL = "/DISPATCHUPS/Customer/servlet/UploadServlet";
	public static final String IMG_Download_URL = "/DISPATCHUPS/Customer/rest/downLoadController/photoDownload?attachment_Id=";
	/**
	 * 服务器接口地址 加密：/DISPATCHUPS/Customer/rest/common/getEncryptApp
	 * 非加密：/DISPATCHUPS/Customer/rest/common/getApp
	 */
	// public static final String WEB_SERVER_URL =
	// (isEncrypt?"/DISPATCHUPS/Customer/rest/common/getEncryptApp":"/DISPATCHUPS/Customer/rest/common/getApp");//"/DISPATCHUPS/Customer/rest/common/getApp";//
	public static final String WEB_SERVER_URL = (isEncrypt ? "/DISPATCHUPS/Customer/rest/common/getSSLInfV100"
			: "/DISPATCHUPS/Customer/rest/common/getApp");
	public static final String Weather_App_key = "fb35dbd0e57719dac07591c4929831f1";
	// 返回登录的广播标识
	public static final String BACK_LOGIN_ACTION = "android.intent.action.BACK_LOGIN";
	/**
	 * 大客户经理项目的APPKEY
	 */
	public static final String App_key = "DKHJL11102";
	public static final String Weather_Url_For_Cityname = "http://apis.baidu.com/heweather/weather/free";

	public static final String ERROR_MESSAGE = "您的网络状况不是很好，数据拉取失败，请重试";
	public static final String LOGIN_CODE = "01001";

	/**
	 * 4.4.1 版本更新
	 */
	public static final String UPDATE_CODE = "04101";
	/**
	 * 告知服务器已同步注册环信
	 */
	public static final String RESULT_HUANXIN_USERNAME = "04139";
	/**
	 * 4.4.2. 外勤签到
	 */
	public static final String SIGNIN_CODE = "04102";
	/**
	 * 4.4.3. 外勤签到统计
	 */
	public static final String SIGNIN_COUNT_CODE = "04103";
	/**
	 * 4.4.4. 外勤签到查看
	 */
	public static final String SIGNIN_CHECK_CODE = "04104";
	/**
	 * 4.4.5. 外勤签到评论
	 */
	public static final String SIGNIN_COMMENT_CODE = "04105";
	/**
	 * 4.4.6. 外勤签到点赞
	 */
	public static final String SIGNIN_PRAISE_CODE = "04106";
	/**
	 * 4.4.7. 外勤签到删除
	 */
	public static final String SIGNIN_DELETE_CODE = "04107";
	/**
	 * 4.4.8.创建公告
	 */
	public static final String NOTIFY_CREATE_CODE = "04108";
	/**
	 * 4.4.9.公告查看
	 */
	public static final String NOTIFY_BROWSE_CODE = "04109";
	/**
	 * 4.4.10.公告评论
	 */
	public static final String NOTIFY_COMMENT_CODE = "04110";
	/**
	 * 4.4.11.公告点赞
	 */
	public static final String NOTIFY_PRAISE_CODE = "04111";
	/**
	 * 4.4.12.公告删除
	 */
	public static final String NOTIFY_DELETE_CODE = "04112";
	/**
	 * 4.4.13. 创建任务:taskCreate
	 */
	public static final String TASK_CREATE_CODE = "04113";
	/**
	 * 4.4.14. 任务查看:taskView
	 */
	public static final String TASK_BROWSE_CODE = "04114";
	/**
	 * 4.4.15. 任务评论:taskComment
	 */
	public static final String TASK_COMMENT_CODE = "04116";
	/**
	 * 4.4.16. 任务点赞:taskPraise
	 */
	public static final String TASK_PRAISE_CODE = "04117";
	/**
	 * 4.4.17. 任务删除:taskDelete
	 */
	public static final String TASK_DELETE_CODE = "04118";
	/**
	 * 4.4.18.获取任务处理人员:taskHandlePerson
	 */
	public static final String TASK_HANDLER_PERSON_CODE = "04119";
	/**
	 * 4.4.19.公告推送
	 */
	public static final String NOTIFY_PUSH_CODE = "04120";

	/**
	 * 4.4.20.任务推送
	 */
	public static final String TASK_PUSH_CODE = "04121";
	/**
	 * 4.4.23 任务回复
	 */
	public static final String TASK_REPLY_CODE = "04124";
	/**
	 * 4.4.24 签到回复
	 */
	public static final String SIGNIN_REPLY_CODE = "04125";
	/**
	 * 4.4.25 公告回复
	 */
	public static final String NOTIFY_REPLY_CODE = "04126";
	/**
	 * 4.4.27 任务点赞取消
	 */
	public static final String TASK_REMOVE_PRAISE_CODE = "04128";
	/**
	 * 4.4.28签到点赞取消
	 */
	public static final String SIGNIN_REMOVE_PRAISE_CODE = "04129";
	/**
	 * 4.4.29 公告点赞取消
	 */
	public static final String NOTIFY_REMOVE_PRAISE_CODE = "04130";
	/**
	 * 4.diy.1.回收率
	 */
	public static final String CRM_RECOVERY_CODE = "04201";
	/**
	 * 4.diy.2.工作简报
	 */
	public static final String CRM_POWER_CODE = "04202";

	/**
	 * 4.1.32.欠费信息查询
	 */
	public static final String WORK_GET_DEPTMENT_MEMBER = "01004";
	/**
	 * 4.1.32.欠费信息查询
	 */
	public static final String CRM_QFCX_CODE = "01020";
	/**
	 * 4.1.33.预收电费查询
	 */
	public static final String CRM_YSDF_CODE = "01022";
	/**
	 * 4.1.21.未缴清电费用户清单
	 */
	public static final String CRM_NOTBEEN_CODE = "01057";
	/**
	 * 4.1.22.未检查用户清单
	 */
	public static final String CRM_NOTCHECK_CODE = "01058";
	/**
	 * 4.1.23.隐患未消除用户清单
	 */
	public static final String CRM_HIDDENDANGERNOT_CODE = "01059";
	/**
	 * 4.1.18.管辖大客户信息查询
	 */
	public static final String CRM_ADMINCLIENT_CODE = "01054";
	/**
	 * 4.1.24.用户未检查明细
	 */
	public static final String CRM_USERNOTCHECK_CODE = "01060";
	/**
	 * 4.1.25.用户隐患未消除明细
	 */
	public static final String CRM_USERHIDDENDANGERNOT_CODE = "01061";
	/**
	 * 4.1.26.用户未缴清电费明细
	 */
	public static final String CRM_USERNOTBEEN_CODE = "01062";
	/**
	 * 4.1.15.客户关系绑定
	 */
	public static final String CRM_CLIENTBOUND_CODE = "01051";
	/**
	 * 4.1.16.客户关系解绑
	 */
	public static final String CRM_CLIENTUNWRAP_CODE = "01062";
	/**
	 * 4.1.2.用户信息
	 */
	public static final String CRM_USERMSG_CODE = "01007";
	/**
	 * 4.1.3.受电点信息
	 */
	public static final String CRM_COMNECTIONMSG_CODE = "01008";
	/**
	 * 4.1.4.电源信息
	 */
	public static final String CRM_POWERMSG_CODE = "01009";
	/**
	 * 4.1.5.计量点信息
	 */
	public static final String CRM_POINTMSG_CODE = "01010";
	/**
	 * 4.1.6.电能表
	 */
	public static final String CRM_AMMETER_CODE = "01011";
	/**
	 * 4.1.7.互感器
	 */
	public static final String CRM_CMUTUALINDUCTOR_CODE = "01012";
	/**
	 * 4.1.8.失压仪
	 */
	public static final String CRM_LOSE_CODE = "01013";
	/**
	 * 4.1.9.变压器
	 */
	public static final String CRM_TRANSFOREER_CODE = "01014";

	/**
	 * 4.1.11.抄表台账
	 */
	public static final String CRM_METERREADING_CODE = "01016";
	/**
	 * 4.1.56.实时电量查询
	 */
	public static final String CRM_METERDAYREADING_CODE = "01045";
	/**
	 * 4.1.40.电量电费查询
	 */
	public static final String CRM_QUERYPOWER_CODE = "01031";
	/**
	 * 4.1.41.用电客户付款记录查询
	 */
	public static final String CRM_QUERYPAY_CODE = "01032";
	/**
	 * 4.1.60.业扩报装预受理查询
	 */
	public static final String CRM_ACCEPTED_CODE = "01077";
	/**
	 * 4.1.48.业务办理进度查询
	 */
	public static final String CRM_QUERYBUSINESS_CODE = "0207116";
	/**
	 * 4.1.57.实时负荷查询（含负荷采集点）
	 */
	public static final String CRM_REALTIMELOAD_CODE = "01046";
	/**
	 * 4.1.58.实时负荷查询（不含负荷采集点）
	 */
	public static final String CRM_7_DAY_REALTIMELOAD_CODE = "01047";
	/**
	 * 4.2.2.业扩申请结果返回
	 */
	public static final String CRM_BUSINESSRESULT_CODE = "04002";
	/**
	 * 4.2.3.业扩环节变更通知
	 */
	public static final String CRM_BUSINESSCHANGE_CODE = "04003";
	/**
	 * 4.2.1.消息接收接口
	 */
	public static final String CRM_MSGTACK_CODE = "04001";
	/**
	 * 4.1.27.客户联系人信息获取
	 */
	public static final String CRM_CLIENTLINKMAN_CODE = "01063";
	/**
	 * 4.1.17.用户查询
	 */
	public static final String CRM_USERQUERY_CODE = "01053";
	/**
	 * 4.1.35.客户经理发布信息统计
	 */
	public static final String CUSMANAGER_PUBLIC_STATISTIC_CODE = "04136";
	/**
	 * 5.2.36.退出登录
	 */
	public static final String LOGIN_OUT = "04137";
	/**
	 * 4.1.1.6 获取工单列表
	 */
	public static final String OBTAIN_JOB_CODE = "01073";
	/**
	 * 4.1.1.2 工单签收
	 */
	public static final String JOB_SIGNIN_CODE = "01002";
	/**
	 * 4.1.28.1 专项检查计划申请
	 */
	public static final String SPECIAL_CHECK_APPLE_CODE = "01064";
	/**
	 * 4.1.28.2 专项检查任务列表下装
	 */
	public static final String SPECIAL_CHECK_LIST_DOWNLOAD_CODE = "01065";
	/**
	 * 4.1.28.3 专项检查任务列表详情下装
	 */
	public static final String SPECIAL_CHECK_DETAIL_DOWNLOAD_CODE = "01074";
	/**
	 * 4.1.28.4 专项检查结果上传
	 */
	public static final String SPECIAL_CHECK_RESULT_UPLOAD_CODE = "01066";
	/**
	 * 4.1.29. 用户档案信息修改申请
	 */
	public static final String CUSTOMINFO_APPLE_MODIFYS_CODE = "01067";
	/**
	 * 4.1.30.1 周期检查任务列表下装
	 */
	public static final String PERIOD_CHECK_LIST_DOWNLOAD_CODE = "01068";
	/**
	 * 4.1.30.2 周期检查任务列表详情下装
	 */
	public static final String PERIOD_CHECK_DETAIL_DOWNLOAD_CODE = "01075";
	/**
	 * 4.1.30.3 周期检查上传
	 */
	public static final String PERIOD_CHECK_RESULT_UPLOAD_CODE = "01069";

	/**
	 * 4.1.61. 停电信息数据获取
	 */
	public static final String POWER_CUT_INFO_SEARCH = "01078";
	/**
	 * 4.4.7. 按人员汇总时效统计
	 */
	public static final String PERSON_INFO_COUNT = "0101007";
	/**
	 * 4.4.1. 获取业扩工单列表
	 */
	public static final String WORK_ORDER_LIST = "0101001";
	/**
	 * 4.4.2. 工单阶段展现
	 */
	public static final String WORK_ORDER_STATUS = "0101002";
	/**
	 * 4.4.12. 工单阶段环节展现
	 */
	public static final String WORK_ORDER_SEGMENT = "0101011";
	/**
	 * 5.2.40.	知识类别列表查询
	 */
	public static final String Konwledge_Category_Search = "04141";
	/**
	 * 	5.2.41.	知识类别下级列表查询
	 */
	public static final String Konwledge_Category_Next_Search = "04142";
	/**
	 * 5.2.42.	知识列表查询
	 */
	public static final String Konwledge_List_Search = "04143";
	/**
	 * 5.2.43.	知识内容查看
	 */
	public static final String Konwledge_Details_Search = "04144";
	/**
	 * 5.2.55.	热门搜索
	 */
	public static final String Konwledge_Hot_Search = "04156";
	/**
	 * 	5.2.44.	知识查询
	 */
	public static final String Konwledge_Search = "04145";
	/**
	 * 5.2.45.	知识阅读记录
	 */
	public static final String Read_Record = "04146";
	/**
	 * 5.2.53.	知识提问内容搜索
	 */
	public static final String Konwledge_question_search = "04154";
	/**
	 * 5.2.46.	知识提问
	 */
	public static final String Konwledge_question = "04147";
	/**
	 * 5.2.47.	知识提问列表查询
	 */
	public static final String Konwledge_question_Item = "04148";
	/**
	 * 5.2.48.	知识提问内容查看
	 */
	public static final String Konwledge_question_Content = "04149";
	/**
	 * 5.2.49.	知识提问回答查看
	 */
	public static final String Konwledge_question_Reply = "04150";
	/**
	 * 5.2.52.	知识提问回答取消点赞
	 */
	public static final String Konwledge_question_Reply_Cancel_Priase = "04153";
	/**
	 * 5.2.51.	知识提问回答点赞
	 */
	public static final String Konwledge_question_Reply_Priase = "04152";
	/**
	 * 5.2.50.	知识提问回答
	 */
	public static final String Konwledge_question_Answer = "04151";

	public static User userInfo;
	/**
	 * 是否切换用户
	 */
	public static boolean changeUser = false;
	/**
	 * 用于区分任务和公告
	 */
	public static int TYPE_NOTICE = 0;
	public static int TYPE_MISSION = 1;
	public static int TYPE_SIGNIN = 2;

	/**
	 * 显示最大点赞人数/评论人数
	 */
	public static int MAX_DISPLAY_COMMENT = 3;
	public static int MAX_DISPLAY_PRAISE = 7;
	/**
	 * 显示点赞全部/部分人数开关
	 */
	public static String SHOW_ALL_PRAISE = "0";
	public static String SHOW_SOME_PRAISE = "1";
	/**
	 * 显示评论全部/部分人数开关
	 */
	public static String SHOW_ALL_COMMENT = "0";
	public static String SHOW_SOME_COMMENT = "1";

	/**
	 * 界面切换开关
	 */
	public static boolean DISPLAY = false;

	/**
	 * activity间传递数据时的key
	 */
	public static final String POSITION = "POSITION";
	public static final String DETAIL = "DETAIL";
	public static final String HXPASSWORD = "9ol.0p;/";
}
