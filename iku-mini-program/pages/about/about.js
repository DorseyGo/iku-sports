// pages/about/about.js
const request  = require("../../utils/request");

Page({

  /**
   * Page initial data
   */
  data: {
    showLearned: true,
    classes: []
  },

  toggleLearned: function() {
    let showLearned = !this.data.showLearned
    this.setData({
      showLearned: showLearned
    })

    this.loadClasses()
  },

  loadClasses: function() {
    let token = 123
    let favorite = (this.data.showLearned) ? 1 : 2
    request.get(`favorite/${favorite}/classes`, {
      token: token
    }).then(res => {
      this.setData({
        classes: res.data
      })
    })
  },

  /**
   * Lifecycle function--Called when page load
   */
  onLoad: function (options) {
    this.loadClasses()
  }
})