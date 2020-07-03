// pages/course/details/details.js
const request  = require("../../../utils/request");
Page({

  /**
   * 页面的初始数据
   */
  data: {
    category: null,
    course: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let category = options.category
    let courseId = options.courseId

    this.setData({
      category: category
    })

    request.get(`courses/${courseId}`).then(res => {
      this.setData({
        course: res.data
      })
    })
  }
})