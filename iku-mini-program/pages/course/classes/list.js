// pages/course/classes/list.js
const request  = require("../../../utils/request");
Page({

  /**
   * Page initial data
   */
  data: {
    curPage: 0,
    offset : 5,
    classes: [],
    course: {},
    title :""
  },

  /**
   * Lifecycle function--Called when page load
   */
  onLoad: function (options) {
    let courseId = 1 //options.courseId
    
    let pageStartNum = this.data.offset

    let pageEndNum  = this.data.curPage

    /** request to fetch course by its id */
    request.get(`classes?courseId=`+courseId+`&&offset=`+pageEndNum+`&&pageSize=`+pageStartNum).then( res =>{
      this.setData({
        classes: res.data
      })
    }, reason => {
      /** rejected */
      console.log(reason)
    }).catch(err => {
      /** if exception detected */
      console.log(err)
    })

    //get title 
    request.get(`courses/`+courseId).then(
      res =>{
        this.setData({
          course : res.data,
          title : res.data.name
        })
    },reason => {
      console.log(reason)
    }).catch(err => {
      console.log(err)
    })
    console.info(this.data.title)
    //set bar title
    wx.setNavigationBarTitle({   
      title: this.data.title
    })
  },

  /**
   * Lifecycle function--Called when page is initially rendered
   */
  onReady: function () {
    wx.setNavigationBarTitle({   
      title: this.data.title
    })

  },

  /**
   * Lifecycle function--Called when page show
   */
  onShow: function () {

  },

  /**
   * Page event handler function--Called when user drop down
   */
  onPullDownRefresh: function (options) {
    let courseId = 1 //options.courseId

    let pageStartNum = this.data.offset*this.data.curPage

    let pageEndNum  = this.data.offset*this.data.curPage+this.data.offset

    this.setData({
      curPage : this.data.curPage+1
    })
    
    request.get(`classes?courseId=`+courseId+`&&offset=`+pageEndNum+`&&pageSize=`+pageStartNum).then( res =>{
      this.setData({
        classes : res.data
      })
    })
  },

  /**
   * Called when page reach bottom
   */
  onReachBottom: function () {

  }
})