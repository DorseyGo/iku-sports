// pages/course/classes/list.js
const request  = require("../../../utils/request");
Page({

  /**
   * Page initial data
   */
  data: {
    curPage: 1,
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
    
    let pageStartNum = this.data.curPage

    let pageEndNum  = this.data.offset

    console.log("classes?courseId="+courseId+"&&offset="+pageEndNum+"&&pageSize="+pageStartNum")
    /** request to fetch course by its id */
    request.get(`classes?courseId=`+courseId+`&&offset=`+pageEndNum+`&&pageSize=`+pageStartNum).then( res =>{
      this.setData({
        course: res.data,
        title : res.data.name
      })
    }, reason => {
      /** rejected */
      console.log(reason)
    }).catch(err => {
      /** if exception detected */
      console.log(err)
    })

    //set bar title
    wx.setNavigationBarTitle({   
      title: this.data.title == null ? "爱酷体育":this.data.title
    })
  },

  /**
   * Lifecycle function--Called when page is initially rendered
   */
  onReady: function () {
    wx.setNavigationBarTitle({   
      title: this.data.title == null ? "爱酷体育":this.data.title
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