package com.yidao.platform.app.base;

/**
 * @Describe 返回数据基类
 * @Author xuhuanli2017@gmail.com
 */
public class BaseResult<T> {
    /**
     * 错误码 1000申请成功,其他失败
     */
    private String errCode;
    /**
     * 错误信息 或许可以直接Toast
     */
    private String info;
    /**
     * 请求状态标记 服务端不一定会修改 不推荐用作判断条件
     */
    private boolean status;

    private T result;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "errCode='" + errCode + '\'' +
                ", info='" + info + '\'' +
                ", status=" + status +
                ", result=" + result +
                '}';
    }
}
