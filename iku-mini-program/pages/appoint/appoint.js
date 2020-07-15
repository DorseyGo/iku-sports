// pages/appoint/appoint.js
const request = require("../../utils/request");

Page({

  /**
   * 页面的初始数据
   */
  data: {
    hasAppoint: false,
    appointCourseInfo: [],
    hasPurchasedAppointCourse: false // 已经购买可以预约
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let userId = wx.getStorageSync('token') || "e9b6ea6f672086252a83a48be2198d63";
    request.get(`appoint/course/list/${userId}`)
      .then(res => {
        console.log(res)
        let hasData = false;
        if (res.data.length > 0) {
          hasData = true;
        }
        this.setData({
          appointCourseInfo: res.data,
          hasPurchasedAppointCourse: hasData
        })
      })
  },

  navigateCoursePlan: function (e) {
    console.log(e);
    // e.currentTarget.dataset 里获取参数
    wx.navigateTo({
      url: './course-plan/course-plan',
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