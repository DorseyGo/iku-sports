// pages/about/about.js
const request  = require("../../utils/request");

const menus = [ {
    "avatar": "../../images/list.png",
    "name": "我的订单",
    "forwardTo": "../order/order",
    "disable": false
  }, {
    "avatar": "../../images/calendar.png",
    "name": "课程预约",
    "forwardTo": "../appoint/appoint",
    "disable": false
  }, {
    "avatar": "../../images/post.png",
    "name": "帖子管理",
    "disable": true
  }, {
    "avatar": "../../images/feedback.png",
    "name": "反馈建议",
    "disable": true
  }, {
    "avatar": "../../images/settings.png",
    "name": "设置",
    "disable": true
  }, {
    "avatar": "../../images/more.png",
    "name": "更多",
    "disable": true
  }
]

Page({

  /**
   * Page initial data
   */
  data: {
    user: null,
    menus: menus,
    hasLearnedCourses: true,
    // 已预约课程数
    userAppointmentNums: 0,
    // 待预约课程数
    userNotYetAppointmentNums: 0
  },

  /**
   * Lifecycle function--Called when page load
   */
  onLoad: function (options) {
    this.userAppointments();
    this.userNotYetAppointments();
  },

  userAppointments: function() {
    let userId = wx.getStorageSync('token') || "e9b6ea6f672086252a83a48be2198d63";
    request.get(`appoint/course/numbers/${userId}`)
           .then(res => {
             console.log(res)
             this.setData({
              userAppointmentNums: res.data
             })
           })
  },

  userNotYetAppointments: function() {
    let userId = wx.getStorageSync('token') || "e9b6ea6f672086252a83a48be2198d63";
    request.get(`appoint/course/not-appointment/${userId}`)
           .then(res => {
             this.setData({
               userNotYetAppointmentNums: res.data
             })
           })
  }

})