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
    learnedCourse: [],
    // 已预约课程数
    userAppointmentNums: 0,
    // 待预约课程数
    userNotYetAppointmentNums: 0,
    numNotifications: 0
  },

  /**
   * Lifecycle function--Called when page load
   */
  onLoad: function (options) {
    
  },

  onShow: function() {
    this.userAppointments();
    this.userNotYetAppointments();
    this.userStudiedClass();
    this.userMessagesNotify();
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
  },

  userStudiedClass: function() {
    let userId = wx.getStorageSync('token') || "e9b6ea6f672086252a83a48be2198d63";
    request.get(`appoint/course/studied/${userId}`)
            .then(res => {
              console.log("userStudiedClass", res)
              let hasStudiedData = false;
              if (res.data.length > 0) {
                hasStudiedData = true;
              }
              this.setData({
                hasLearnedCourses: hasStudiedData,
                learnedCourse: res.data
              })
            })
  },

  userMessagesNotify: function() {
    let userId = wx.getStorageSync('token') || "e9b6ea6f672086252a83a48be2198d63";
    request.get(`massage/notify/count/${userId}`)
           .then(res => {
             this.setData({
              numNotifications: res.data
             })
           })
  }

})