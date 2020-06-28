package com.rysh.module.commonService.config;

public class AlipayConfig {

    // 商户appid
    public static final String APPID = "2016101700707748";
    // 私钥 pkcs8格式的
    public static  final String RSA_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCkndzr2Ug1Txc1myDsiBv3GSjIgJy6xHtvN0O/jZzLojAjzWVfj1hQLN/zSgkI7RBlaf42fOW5x/HXCHkYHHbRhNvhSs3+hEHoP7LEDYyCmuMPGMPSzQ9M2ryWKDuE3MwbsWLkttQrsgf0V64f7evg+lsVgUjW6qyRf+65X3MMkb3cCPLhSHRmwchKuPaE4A+7OHU4ZOkoxy+G6Uge7Ie1VsHf1m/5ofXHkPbq4IrpAbQ91y1V7hTLZ+XN2twdpN+SuVcJnyjSUJuddzrvGSthwFvlp1xn/NntxG05zkRyT9gtGvOZLTPAbmWEBV1Q0704Fc0DjoEglo+NvqS19sN3AgMBAAECggEARPRLYg+2C36Bvlaox+uLq0nCp5oYDLPY8JE5s7diAnqBp72VxR2KZKsndAWDG1YSFCj9710J7XQvfV9zOgJSUxlP8WMgPoiwAtUL0MvMG7k9e0loXZi/2/WUIWHg7JqAdOks/5kF7ve4FUXVDlLfB3JXTP7GwB+1AY2RNd0hmBWXkQnqEMAh+CXkSQHEHGW1XzVL7+VvOwFB/ooPjHoFT+DvPlPt6JyEafctuo27pBgIFxQmIIdvFNWP47EVqEWrkdCYLVgJfD0CZ0e0d4HYNvGbI2KyEYq3FdHkNDFhyLMMR/a4225W2rGqIj1KmfRYkbrHBiBoGLI/l377QWhr4QKBgQDjJdPAIABuJ8t9z4Qc8TK/LyKJ1m0e1q101MIhr1nnLPGAWeOfTUW0NJ3HJ07V7olVk8UWJi9Po97SROFR4sOdcJT80LakaOZZ1YhTpidMRNvyewcvARXimgp/e/tcfB3STXJiV23frvx+W6N8RyLEeFPM7F+PL9GXOAWvug9D1QKBgQC5hrWPnF6kGmht7Y26mLIcKtpk7+MiKVzc1CJRm3ZC421u44IgC7e1tSjBHJNT5yathyd9iRFSaaCub7ZsCltihA6Mv2pWV4Y+5/Sc6cRnVJqJE4J4yTi5oBxPo2/aLZSQdTWYu/3mIY3onQeG7K45ol7PB9uXxca4dV4QnuosGwKBgFwapIgM8d+4xyyWjo1lyBv5stmNVuXgSqlK9ATQo3W3pZ1T1ZZssMznYOq2J6Nbe9h1eL8IcLqsSdedEngvqP4X/nGc99ImrvDSQtsYTmsAqObjt//5QrSFnagfz8aqlTUHLlBt/yFqRPu/kMT92z0rRV+coQrgau4US0n7+eCZAoGAG+GTvY5ZZr4VPZf6ubUi5zrFcW/fqY2fyAwVUEBVyH2XP5fvQVOheyxLgKIDvhM569Ao7iPAN6tqG2zsdFYmH6sK6LoHiAxFP4TqbP5wprq/MqR88hwDa1oVhx5fu+Ent4H12Qs/f+tBAVk//VXqZPzy1JgwtyFg2O3CgirvtEsCgYBnrcu9aAAE/rWQtseYTdq1FSYxpNu0XS7RRPMAdeP8SCRAf8+EWF+tRnNumEoyx8uniKrKvIZs/3o/q/BxYyW3kufd7dvze/vKCXKvfh6MIDFu+9SZLUccz71NgQAPI/9SNjiDrR9TEwGqKGGlatzHqPJbPUqbIauhGlSKSnyU2g==";
    // 支付宝公钥
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgG9i4QJF25DELMergLQKyhATPv2UtgUrQN5Rhynzw2bCSCvuZY0iFfM7e7Q91WrDArQsDtTy6Hz2OTqGMW/o25yeCo56P2mS8H971JypAWKLsSPwr3Z4deZ6ewA/MCTm6lORGnm2ry1oDrPzBWgoZaFhyIyqh3Qyj4Hqx3UoQ0byjIHKD4BJH0tcO4Lz92ByqKrP9wIblTy78A7cSizLQ1ToPZTE4vSjybouR5URIlcHVVoB5AcGWemAx19BlU+E+r+apMDSQcPK31c5XytKZLhvOnm5CyPR+lA5q+5bdnWILnfQsmuDOO4oAKTtI01kCH5iOhZgNYnLvjhxJ32nvwIDAQAB";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static final String NOTIFY_URL = "https://hsiangsun.easy.echosite.cn/server/alipay/notify";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static final String RETURN_URL = "https://hsiangsun.easy.echosite.cn/server/alipay/return";
    // 请求网关地址
    public static final String URL = "https://openapi.alipaydev.com/gateway.do";
    // 编码
    public static final String CHARSET = "UTF-8";
    // 返回格式
    public static final String FORMAT = "json";
    // 日志记录目录
    public static final String LOG_PATH = "/logs";
    // RSA2
    public static final String SIGNTYPE = "RSA2";
}
