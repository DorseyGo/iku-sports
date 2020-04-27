// pages/course/coach/coach.js
const request =  require("../../../utils/request");
Page({

  /**
   * 页面的初始数据
   */
  data: {
    coachId : 0,
    coach : {},
    classes : {},
    favoriteType: 4,
    userId:"1",
    isFavorite: 0,
    totalNumOffavorite: 0
  },

  /**
   * get coash information
   */
  getCoach : function() {
    request.get(`coaches/`+ this.data.coachId).then(res =>{
      this.setData({
        coach: res.data
      })
      
    },reason => {
      console.log(reason)
    }).catch(err => {   
      console.log(err)
    })
  },

  favoriteSummaryInformation: function(params){
    request.post(`favorite/summary`,params).then(res =>{
      this.setData({
        totalNumOffavorite: res.data
      },reason =>{
        console.log(reason)
      })
    }).catch(err =>{
      console.log(err)
    })
  },
  favoriteAction: function(){
    let params = {
      favoriteId: this.data.coachId,
      favoriteType:this.data.favoriteType,
      userId:this.data.userId
    }
    this.favoriteSummaryInformation(params)
    if (this.data.totalNumOffavorite === 0 ){
      request.post(`favorite/add`,params).then(res =>{
        this.setData({
          totalNumOffavorite: 1
        });
        this.requestShowToast()
      },reason => {
        console.log(reason)
      }).catch(err => {
        console.log(err)
      })
    }else{
      request.post(`favorite/del`,params).then(res => {
        this.setData({
          totalNumOffavorite: 0
        });
        this.requestShowToast()
      },reason =>{
        console.log(reason)
      }).catch(err => {
        console.log(err)
      })
    }
  }
  ,
  /**
   * showToast of request
   */
  requestShowToast: function () {
    if( this.data.totalNumOffavorite === 0 ){
      wx.showToast({
        title: '取消关注',
        icon: 'none',
        duration: 1500
      })
    }else{
      wx.showToast({
        title: '已关注',
        icon: 'none',
        duration: 1500
      })
    }
  }
  ,
  /**
   * get coaching
   */
  getCoachClass : function(){
    let coachId = this.data.coachId
    request.get(`coaches/${coachId}/classes/`).then(res =>{
      this.setData({
        classes: res.data
      })
    })
  }
  ,
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options.coachId)
    this.setData({
      coachId: options.coachId
    })

    this.getCoach()
    this.getCoachClass()
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})