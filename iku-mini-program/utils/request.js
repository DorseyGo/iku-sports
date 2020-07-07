const config = require("../config");
const errors  = require("./errorCodes");

const request = (uri, options) => {
    return new Promise((resolve, reject) => {
        wx.request({
            url: `${config.api.url}${uri}`,
            data: (options.method === 'GET') ? options.data : JSON.stringify(options.data),
            header: options.method === 'GET' ?{'content-type':'application/json'} 
                : {'content-type':'application/json'},
            method: options.method,
            dataType: 'json',
            success: (result)=>{
                if (result.data.statusCode === 0) {
                    resolve(result.data)
                    return
                }

                wx.showToast({
                    title: errors.getError(result.data.statusCode) || "未知错误，请联系管理员",
                    icon: 'none'
                });
            },
            fail: (error) => {reject(error.data)}
        });
    });
}

const get = (uri, options = {}) => {
    return request(uri, {
        method: "GET", 
        data: options
    })
}

const post = (uri, options = {}) => {
    return request(uri, {
        method: "POST",
        data: options
    })
}

const put = (uri, options = {}) => {
    return request(uri, {
        method: "PUT", 
        data: options
    })
}

const del = (uri, options = {}) => {
    return request(uri, {
        method: "DELETE",
        data: options
    })
}

module.exports = {
    get,
    post,
    put,
    del
}