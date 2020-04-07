// pages/course/classes/list.js
const request  = require("../../../utils/request");
Page({

  /**
   * Page initial data
   */
  data: {
    curPage: 1,
    classes: [],
    course: {}
  },

  /**
   * Lifecycle function--Called when page load
   */
  onLoad: function (options) {
    let courseId = options.courseId
    /** request to fetch course by its id */
    request.get(`courses/` + courseId).then(res => {
      this.setData({
        course: res.data
      })
    }, reason => {
      /** rejected */
      console.log(reason)
    }).catch(err => {
      /** if exception detected */
      console.log(err)
    })
  },

  /**
   * Lifecycle function--Called when page is initially rendered
   */
  onReady: function () {

  },

  /**
   * Lifecycle function--Called when page show
   */
  onShow: function () {

  },

  /**
   * Page event handler function--Called when user drop down
   */
  onPullDownRefresh: function () {

  },

  /**
   * Called when page reach bottom
   */
  onReachBottom: function () {

  }
})