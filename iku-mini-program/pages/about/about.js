// pages/about/about.js
const request  = require("../../utils/request");

Page({

  /**
   * Page initial data
   */
  data: {
    userInfo: {},
    showLearned: true,
    classes: [],
    user: {},
    userId: "e9b6ea6f672086252a83a48be2198d63",
    list:[{
      url:"../../images/header.jpeg",
      name:"初级网球课"
    },{
      url:"../../images/header.jpeg",
      name:"初级网球课"
    },
    {
      url:"../../images/header.jpeg",
      name:"初级网球课"
    }]
  },

  toggleLearned: function() {
    let showLearned = !this.data.showLearned
    this.setData({
      showLearned: showLearned
    })

    this.loadClasses()
  },

  loadClasses: function() {
    let token = this.data.userId
    let reqUrl = (this.data.showLearned) ? `watched/classes` : `favorite/classes`
    request.get(`${reqUrl}`, {
      token: token
    }).then(res => {
      this.setData({
        classes: res.data
      })
    })
  },

  loadUser: function() {
    let token = this.data.userId
    request.get(`users/${token}`).then(res => {
      this.setData({
        user: res.data
      })
    })
  },

  /**
   * Lifecycle function--Called when page load
   */
  onLoad: function (options) {
    // this.loadUser()
    wx.login({
      success (res) {
        console.log(res.code)
        if (res.code) {
          //发起网络请求
          wx.request({
            url: 'http://localhost:9080/sports/user/login',
            data: {
              code: res.code
            }
          })
        } else {
          console.log('登录失败！' + res.errMsg)
        }
      }
    })
    this.loadClasses();
  },

  handleGetUserInfo: function (e) {
    console.log(e);
    console.log(e.detail.userInfo.avatarUrl)
    wx.setStorageSync('userinfo', e.detail.userInfo);
    this.setData({
      userInfo: e.detail.userInfo,
    })
  },

  onShow: function(options) {
    const userinfo = wx.getStorageSync('userinfo')
    this.setData({
      userInfo: userinfo,
      hasUserInfo: true
    })
  }
})