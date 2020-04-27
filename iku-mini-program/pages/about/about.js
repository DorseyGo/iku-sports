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
  },

  /**
   * Lifecycle function--Called when page load
   */
  onLoad: function (options) {
    let token = 123
    let favorite = (this.data.showLearned) ? 1 : 2
    request.post(`favorite/classes", {
      token: token,
      favoriteType: favorite
    }).then(res => {
      this.setData({
        classes: res.data
      })
    })
  },

  /**
   * Lifecycle function--Called when page is initially rendered
   */
  onReady: function () {

  }
})