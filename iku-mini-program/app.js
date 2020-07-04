//app.js
const request  = require("./utils/request");
const env  = require("./utils/env");

App({
  onLaunch: function () {
    env.chkSession(
      wx.login({
        success: (result)=>{
          if (result.code) {
            /** request to login and fetch the openid and session key */
            console.log("==> Login and fetched code:" + result.code)
            request.post(`user/login`, {
              code: result.code
            }).then(res => {
              /** succeed */
              if (res.data.token) {
                wx.setStorageSync('token', res.data.token);
              }
            })
          }
        },
        fail: ()=>{
          /** fail to login */
          wx.showToast({
            title: '登录失败，请检查网络是否已打开',
            icon: 'none'
          });
        }
      })
    ) 
  },

  globalData: {
    userInfo: null
  }
})