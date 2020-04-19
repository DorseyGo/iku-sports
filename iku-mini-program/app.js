//app.js
const request  = require("./utils/request");
const server  = require("./utils/server");

App({
  onLaunch: function () {
    server.chkSession(() => {

      wx.login({
        timeout: 10000,
        success: (result) => {
          request.get(`user/login`, {
            code: result.code
          }).then(res => {
            wx.setStorageSync('iku_sports_session', res.data.session_key)
          })
        },
        fail: () => {
          console.log('Failed to login within use wx.login API')
        }
      })

    })
  },

  onGetSetting: () => {
    wx.getSetting({
      success: (result) => {
        if (result.authSetting['scope.userInfo']) {
          wx.getUserInfo({
            success: (result) => {
              console.log(result)  
            }
          })
        } else {
          wx.reLaunch({
            url: '/pages/authorize/authorize'
          });
            
        }
      }
    });
      
  },
  

  globalData: {
    userInfo: null
  }
})