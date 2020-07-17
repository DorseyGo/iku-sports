const request = require("../../utils/request")

// pages/teaching-style/teaching-style.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    teachingStyle: null,
    teachingStyles: [],
    watches: 0,
    styleId: -1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let styleId = options.styleId
    this.setData({
      styleId: styleId
    })

    request.get(`teaching-styles/${styleId}`).then(res => {
      this.setData({
        teachingStyle: res.data
      })
    })

    request.get(`teaching-styles/promotions/${styleId}`).then(res => {
      this.setData({
        teachingStyles: res.data
      })
    })
  },

  updateWatches: function() {
    let watches = this.data.watches
    let styleId = this.data.styleId
    request.put(`teaching-styles/${styleId}`, {
      watches: watches
    }).then(res => {
      console.log("Succeed update watches")
    })
  }
})