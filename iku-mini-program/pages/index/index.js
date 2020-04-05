//index.js
//获取应用实例
const app = getApp()
const request  = require("../../utils/request");

Page({
  data: {
    curCategory: "basketball",
    categories: [],
    courses: [],
    activities: []
  },

  onLoad: function () {
    /** request to fetch categories */
    request.get(`categories`).then(res => {
      this.setData({
        categories: res.data
      })
    })

    /** request to fetch activities */
    request.get(`activities`).then(res => {
      this.setData({
        activities: res.data
      })
    }).catch(err => {
      console.log(err)
    })

    this.loadCoursesByCategoryName()
  },

  loadCoursesByCategoryName: function() {
    /** request to fetch courses by category name */
    request.get(`courses/category?name=` + this.data.curCategory).then(res => {
      this.setData({
        courses: res.data
      })
    }, reason => {
      console.log("rejected")
      this.setData({
        courses: []
      })
    }).catch(err => {
      console.log(err)
    }).re
  },

  navTo: function(e) {
    let cate = e.target.dataset.cur;
    this.setData({
      curCategory:  cate
    })

    this.loadCoursesByCategoryName()
  }
})
