//index.js
//获取应用实例
const app = getApp()
const request  = require("../../utils/request");

Page({
  data: {
    categories: [],
    activities: []
  },

  onLoad: function () {
    request.get(`categories`).then(res => {
      this.setData({
        categories: res.data
      })
    })

    request.get(`activities`).then(res => {
      this.setData({
        activities: res.data
      })
    })
  }
})
