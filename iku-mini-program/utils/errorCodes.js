var errorCodes = new Map();
errorCodes.set(-1, "请求参数错误");
errorCodes.set(2000, "请求错误，请稍后重试");
errorCodes.set(2001, "网络错误，请稍后重试");

const getError = (code) => {
    return errorCodes.get(code);
}

module.exports = {
    getError: getError
}