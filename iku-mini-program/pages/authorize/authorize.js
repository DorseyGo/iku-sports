// pages/authorize/authorize.js
const request  = require("../../utils/request");
const app =  getApp();

Page({

  /**
   * Page initial data
   */
  data: {
    canIUse : wx.canIUse('button.open-type.getUserInfo')
  },

  bindGetUserInfo: function(e) {
    if (e.detail.userInfo) {
      this.saveUserInfo(e.detail.userInfo)
      wx.switchTab({
        url: '../index/index'
      });
        
    } else {
      wx.showModal({
        title: '警告',
        content: '您点击了取消授权，将无法进入小程序，请授权后再进入',
        showCancel: true,
        cancelText: '取消',
        cancelColor: '#000000',
        success: (result) => {
          if (result.confirm) {
            console.log('User clicked returned back')
          }
        },
        fail: () => {},
        complete: () => {}
      });
        
    }
  },

  saveUserInfo: function(userInfo) {
    app.globalData.userInfo = userInfo
    request.post(`user`, {
      nickName: userInfo.nickName,
      avatarUrl: userInfo.avatarUrl,
      gender: userInfo.gender,
      province: userInfo.province,
      city: userInfo.city,
      country: userInfo.country
    })
  }
})