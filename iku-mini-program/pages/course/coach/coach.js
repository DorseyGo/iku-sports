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
    favoriteType: 3,
    userId:""
  },

  /**
   * get coash information
   */
  getCoach : function() {
    return request.get(`coaches/`+ this.data.coachId).then(res =>{
      this.setData({
        coach: res.data
      })
      
    },reason => {
      console.log(reason)
    }).catch(err => {   
      console.log(err)
    })
  },
  // favoriteSummaryInformation: function(params){
  //   // obtian identity information
  //   // let app =  getApp();
  //   // this.setData({
  //   //   userId: app.userId    
  //   // })
  //   let params = {
  //     favoriteId:this.data.coachId,
  //     favoriteType:this.data.favoriteType,
  //     userId:this.data.userId
  //   }
  //   return request.post(`favorite/summary`,params).then(res =>{
  //     this.setData({
  //       totalNumOffavorite: res.data
  //     },reason =>{
  //       console.log(reason)
  //     })
  //   }).catch(err =>{
  //     console.log(err)
  //   })
  // },
  /**
   * get coaching
   */
  getCoachClass : function(){
    request.get(`classes/`+ this.data.coachId).then(res =>{
      this.setData({
        classes: res.data
      }),reason =>{
        console.log(reason)
      }
      }).catch(err =>{
        console.log(err)
    })
  }
  ,
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //this.data.coachId = options.coachId
    this.data.coachId = 1
    this.getCoach().then(() =>{
      
    })
    this.getCoachClass()
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

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