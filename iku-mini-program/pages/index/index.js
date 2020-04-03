//index.js
//获取应用实例
const app = getApp()
const request  = require("../../utils/request");

Page({
  data: {
    curCategory: "basketball",
    categories: [],
    activities: []
  },

  onLoad: function () {
    request.get(`categories`).then(res => {
      this.setData({
        categories: res.data
      })
    })
  }
})
