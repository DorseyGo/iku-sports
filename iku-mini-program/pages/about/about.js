// pages/about/about.js
const request  = require("../../utils/request");

Page({

  /**
   * Page initial data
   */
  data: {
    showLearned: true,
    classes: [],
    user: {},
    userId: "e9b6ea6f672086252a83a48be2198d63"
  },

  toggleLearned: function() {
    let showLearned = !this.data.showLearned
    this.setData({
      showLearned: showLearned
    })

    this.loadClasses()
  },

  loadClasses: function() {
    let token = this.data.userId
    let reqUrl = (this.data.showLearned) ? `watched/classes` : `favorite/classes`
    request.get(`${reqUrl}`, {
      token: token
    }).then(res => {
      this.setData({
        classes: res.data
      })
    })
  },

  loadUser: function() {
    let token = this.data.userId
    request.get(`users/${token}`).then(res => {
      this.setData({
        user: res.data
      })
    })
  },

  /**
   * Lifecycle function--Called when page load
   */
  onLoad: function (options) {
    this.loadUser()
    this.loadClasses()
  }
})