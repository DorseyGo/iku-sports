// pages/course/details/details.js
const request  = require("../../../utils/request");
Page({

  /**
   * 页面的初始数据
   */
  data: {
    category: null,
    course: null,
    customServicePhoneNo: null,
    customService: "custom_service"
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let category = options.category
    let courseId = options.courseId
    let customService = this.data.customService

    this.setData({
      category: category
    })

    request.get(`courses/${courseId}`).then(res => {
      this.setData({
        course: res.data
      })
    })

    request.get(`config/${customService}`).then(res => {
      this.setData({
        customServicePhoneNo: res.data.value
      })
    })
  },

  contact: function() {
    let phone = this.data.customServicePhoneNo || "18998815018"
    wx.makePhoneCall({
      phoneNumber: phone,
      fail: () => {
        wx.showToast({
          title: '联系客服失败，请稍后重试！',
          icon: 'none'
        });
      }
    });
      
  }
})