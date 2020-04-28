// pages/course/classes/list.js
const request = require("../../../utils/request");
Page({

  /**
   * Page initial data
   */
  data: {
    curPage: 1,
    classes: [],
    courseId: 0,
    course: {}
  },

  loadData: function () {
    let courseId = this.data.courseId
    request.get(`courses/${courseId}/classes`, {
      curPage: this.data.curPage
    }).then(res => {
      this.setData({
        curPage: ++this.data.curPage,
        classes: res.data
      })
    })
  },

  loadCourse: function() {
    let courseId = this.data.courseId
    request.get(`courses/${courseId}`).then(res => {
      this.setData({
        course: res.data
      })

      wx.setNavigationBarTitle({
        title: res.data.name
      });
    })
  },

  /**
   * Lifecycle function--Called when page load
   */
  onLoad: function (options) {
    let courseId = options.courseId
    this.setData({
      courseId: courseId
    })

    this.loadData()
    this.loadCourse()
  },

  /**
   * Page event handler function--Called when user drop down
   */
  onPullDownRefresh: function () {
    this.setData({
      curPage: 1
    })

    this.loadData()
  },

  /**
   * Called when page reach bottom
   */
  onReachBottom: function () {
    wx.showLoading({
      title: 'Loading...',
    })

    this.loadData()
    console.log(this.data.classes.length)
    if (this.data.classes.length == 0) {
      wx.showLoading({
        title: '客官，已到底了',
        mask: true
      });
    } else {
      wx.hideLoading();
    }
  },

  onShow: function () {
    if (this.data.course.name) {
      wx.setNavigationBarTitle({
        title: this.data.course.name
      });
        
    }
  }
})