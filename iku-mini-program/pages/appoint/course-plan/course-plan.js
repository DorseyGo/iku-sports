// pages/appoint/course-plan/course-plan.js
const request = require("../../../utils/request");

Page({

  /**
   * 页面的初始数据
   */
  data: {
    arrangedClasses: [],
    hasArrangedClasses: true,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let userId = wx.getStorageSync('token');
    // userId = 'e9b6ea6f672086252a83a48be2198d63'
    let courseId = options.courseId;

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
  },

  /**
   * 预约
   * @param {*} event 
   */
  appointClass: function(event) {
    let userId = wx.getStorageSync('token');
    // userId = 'e9b6ea6f672086252a83a48be2198d63'
    
    console.log(event)
    let courseId = event.currentTarget.dataset.courseid
    console.log(courseId)
    request.post(`appoint/course/class`, {
      userId: userId,
      arrangeClassId: event.currentTarget.dataset.arrangeclassid
    }).then(res => {
      this.list(courseId, userId)
    })
  },

  cancelAppoint: function(event) {
    let userId = wx.getStorageSync('token');
    // userId = 'e9b6ea6f672086252a83a48be2198d63'

    console.log(event)
    let courseId = event.currentTarget.dataset.courseid
    console.log(courseId)
    request.post(`appoint/course/cancel`, {
      userId: userId,
      arrangeClassId: event.currentTarget.dataset.arrangeclassid
    }).then(res => {
      this.list(courseId, userId)
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})