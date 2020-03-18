const config = require("../config");

const request = (uri, options) => {
    return new Promise((resolve, reject) => {
        wx.request({
            url: `${config.api.url}${uri}`,
            data: options.method === 'GET' ? options.data : JSON.stringify(options.data),
            header: {'content-type':'application/json'},
            method: options.method,
            dataType: 'json',
            success: (result)=>{
                if (result.data.statusCode === 0) {
                    resolve(result.data)
                    return
                }

                reject(result.data)
            },
            fail: (error) => {reject(error.data)},
            complete: () => {
                console.log("Request to uri completed");
            }
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