// pages/course/course.js
const request  = require("../../utils/request");
Page({

  /**
   * 页面的初始数据
   */
  data: {
    courses: [],
    category: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let categoryId = options.categoryId
    request.get(`categories/${categoryId}`).then(res => {
      this.setData({
        category: res.data
      })

      wx.setNavigationBarTitle({
        title: res.data.displayName
      });
        
    })

    /** request to fetch courses according to category id */
    request.get(`categories/${categoryId}/courses`).then(res => {
      this.setData({
        courses: res.data
      })
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  }
})