package com.llw.goodweather;

public class Constant {

    public final static String API_KEY = "7adea386e27c4bccbac0c69bbca17d40";//和风天气api


    public static final String SUCCESS = "200";//和风天气接口请求成功状态码200


    public static final String FIRST_RUN = "firstRun";//程序第一次运行，会比之后运行有所区别，需要分开处理


    public static final String FIRST_STARTUP_TIME_TODAY = "firstStartupTimeToday";//第一次启动时间

    public static final String USED_BING = "usedBing";//是否适应biying壁纸


    public static final String BING_URL = "bingUrl";//必应图片地址


    public static final String LOCATION_CITY = "locationCity";//当前定位城市


    public static final String CITY_RESULT = "cityResult";//页面返回城市效果

  //管理城市模块中所推荐的城市
    public static final String[] CITY_ARRAY = {"北京", "上海", "广州", "深圳", "天津", "武汉", "长沙", "重庆", "沈阳", "杭州",
            "南京", "沈阳", "哈尔滨", "长春", "呼和浩特", "石家庄", "银川", "乌鲁木齐", "呼和浩特", "拉萨", "西宁", "西安", "兰州", "太原",
            "昆明", "南宁", "成都", "济南", "南昌", "厦门", "扬州", "岳阳", "赣州", "苏州", "福州", "贵阳", "桂林", "海口", "三亚", "香港",
            "澳门", "台北"};
    public static final String ROTATION="rotation";//音乐播放器动画常量

}
