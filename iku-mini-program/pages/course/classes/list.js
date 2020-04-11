// pages/course/classes/list.js
const request  = require("../../../utils/request");
Page({

  /**
   * Page initial data
   */
  data: {
    curPage : 0,
    offset : 10,
    classes : [],
    course : {},
    pageStartNum: 0,
    pageEndNum: 0,
    totalNum: 0,
    chapter: 0
  },

  loadData : function(options) {
    let courseId = 1 //options.courseId
    this.data.pageStartNum = this.data.curPage * this.data.offset
    this.data.pageEndNum = this.data.pageStartNum + this.data.offset
    return request.get(`classes?courseId=` + courseId + `&&pageSize=` + this.data.pageEndNum + `&&offset=` + this.data.pageStartNum ).then(res => {
      this.setData({
        classes: this.data.curPage === 0 ? res.data:this.data.classes.concat(res.data),
        curPage: ++this.data.curPage,
        offset: this.data.offset * this.data.curPage
      })
    }, reason => {
     /** rejected */
      console.log(reason)
   }).catch(err => {
      /** if exception detected */
      console.log(err)
    })
    },
    
    setTitle(options) {
      let courseId = 1 //options.courseId
      return request.get(`courses/` + courseId).then(
        res => {
          this.setData({
          course: res.data,
          })
        }, reason => {
            console.log(reason)
        }).catch(err => {
             console.log(err)
       })
    },


    GetSummaryInformation(options){
      let courseId = 1 //options.courseId
      request.get(`classes/count/` + courseId).then(res =>{
        this.setData({
          totalNum: res.data.totalCnt,
          chapter: res.data.chapter
        })
      },reason =>{
        console.log(reason)
      }).catch(err =>{
        console.log(err)
      })
    },
  /**
   * Lifecycle function--Called when page load
   */
    onLoad: function () {

      this.loadData()

      this.GetSummaryInformation()

      this.setTitle().then(() =>{
        wx.setNavigationBarTitle({
          title: this.data.course.name,
        })
      })
  },
  /**
   * Page event handler function--Called when user drop down
   */
  onPullDownRefresh: function () {
    this.setData({
      curPage : 0
    })
    this.loadData().then(() => wx.stopPullDownRefresh())
  },

  /**
   * Called when page reach bottom
   */
  onReachBottom: function () {
    wx.showLoading({
      title: 'Loading...',
    })

    if(this.data.totalNum > this.data.pageEndNum){
      this.loadData().then(() => {
        wx.hideLoading()
      })
    }else{
      wx.showToast({
        title: '客官，已经到底了～',
        icon: "none"
      })
    }
      

    
  }
})