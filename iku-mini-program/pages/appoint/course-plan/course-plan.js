// pages/appoint/course-plan/course-plan.js
const request = require("../../../utils/request");

Page({

  /**
   * 页面的初始数据
   */
  data: {
    arrangedClasses: [],
    hasArrangedClasses: false
  },

  currentCourseId: -1,

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let userId = wx.getStorageSync('token');
    userId = 'e9b6ea6f672086252a83a48be2198d63'
    let courseId = options.courseId;
    this.currentCourseId = courseId

    this.list(courseId, userId);
  },

  list: function(courseId, userId) {
    request.get(`arrange/class/${courseId}?userId=${userId}`)
           .then(res => {
             console.log("list" + res)
            let hasData = false;
            if (res.data.length > 0) {
              hasData = true;
            }

            this.setData({
               arrangedClasses: res.data,
               hasArrangedClasses: hasData
            })
          })

    wx.stopPullDownRefresh();
  },

  /**
   * 预约
   * @param {*} event 
   */
  appointClass: function(event) {
    let userId = wx.getStorageSync('token');
    userId = 'e9b6ea6f672086252a83a48be2198d63'
    
    console.log(event)
    
    request.post(`appoint/course/class`, {
      userId: userId,
      arrangeClassId: event.currentTarget.dataset.arrangeclassid
    }).then(res => {
      wx.showToast({
        title: '预约成功',
        icon: 'success',
        duration: 3000
      });
      
      this.list(this.currentCourseId, userId)
    })
  },

  cancelAppoint: function(event) {
    let userId = wx.getStorageSync('token');
    userId = 'e9b6ea6f672086252a83a48be2198d63'

    console.log(event)
    let arrangeClassId = event.currentTarget.dataset.arrangeclassid
    wx.showModal({
      title: '提示',
      content: '真的要取消课程预约吗？',
      showCancel: true,
      cancelText: '取消',
      cancelColor: '#000000',
      confirmText: '确定',
      confirmColor: '#3CC51F',
      success: res => {
        if (res.confirm) {
          this.doCancelAppoint(userId, arrangeClassId)
        }
      }
    })
  },

  doCancelAppoint: function(userId, arrangeClassId) {
    request.post(`appoint/course/cancel`, {
      userId: userId,
      arrangeClassId: arrangeClassId
    }).then(res => {
      wx.showToast({
        title: '取消预约成功',
        icon: 'success',
        duration: 3000
      })

      this.list(this.currentCourseId, userId)
    })
  },

  /**
   * 监听用户下拉动作
   */
  onPullDownRefresh: function () {
    let userId = wx.getStorageSync('token');
    userId = 'e9b6ea6f672086252a83a48be2198d63'
    
    this.list(this.currentCourseId, userId)
  },
})