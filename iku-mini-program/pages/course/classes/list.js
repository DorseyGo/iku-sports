// pages/course/classes/list.js
const request  = require("../../../utils/request");
Page({

  /**
   * Page initial data
   */
  data: {
    curPage : 0,
    offset : 1,
    pageSize : 10,
    classes : [],
    course : {},
    pageStartNum: 0,
    pageEndNum: 0,
    totalNum: 0,
    chapter: 0,
    courseId: 1
  },

  loadData : function() {
    this.data.pageStartNum = this.data.offset
    return request.get(`classes?courseId=` + this.data.courseId + `&pageSize=` + this.data.pageSize + `&offset=` + this.data.pageStartNum ).then(res => {
      this.setData({
        classes: this.data.curPage === 0 ? res.data:this.data.classes.concat(res.data),
        curPage: ++this.data.curPage,
        offset: this.data.curPage * (++this.data.pageSize)+1
      })
      ,
      console.log("curpage:"+ this.data.curPage+",offset:"+this.data.offset)
    }, reason => {
     /** rejected */
      console.log(reason)
   }).catch(err => {
      /** if exception detected */
      console.log(err)
    })
    },
    
    setTitle() {
      return request.get(`courses/` + this.data.courseId).then(
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


    GetSummaryInformation(){
      request.get(`classes/count/` + this.data.courseId).then(res =>{
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
    onLoad: function (options) {
      let courseId = options.courseId
      this.setData({
        courseId: courseId
      })

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
      curPage : 0,
      offset :0
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

    if(this.data.totalNum > this.data.offset){
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