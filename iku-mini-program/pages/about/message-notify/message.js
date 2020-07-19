// pages/about/message-notify/message.js
const request  = require("../../../utils/request");

Page({

  /**
   * 页面的初始数据
   */
  data: {
    messagesNotify: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.messagesList();
  },

  messagesList: function() {
    let userId = wx.getStorageSync('token') || "e9b6ea6f672086252a83a48be2198d63";
    request.get(`massage/notify/history/${userId}`)
           .then(res => {
             console.log('message notify', res)
             this.setData({
              messagesNotify: res.data
             })
           })
  }
})