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
    menus: menus
  },

  /**
   * Lifecycle function--Called when page load
   */
  onLoad: function (options) {
  }

})