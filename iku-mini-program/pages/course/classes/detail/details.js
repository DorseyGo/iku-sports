// pages/course/classes/detail/details.js
const request  = require("../../../../utils/request");

Page({

  /**
   * 页面的初始数据
   */
  data: {
    classId : 1,
    class : {},
    favorite : {},
    favoriteType: 1,
    watchNumAstrict: 0,
    userId: 1,
    totalNumOffavorite: 0,
    favoriteText: "收藏"
  },
  
  loadData: function(){
    let classId = this.data.classId
      return request.get(`class/`+classId).then(res => {
        this.setData({
          class : res.data
        })
      },reason =>{
        console.log(reason)
      }).catch(err =>{
        console.log(err)
      })
  }
  ,
  /**
   * click to trigger add then event
   */
  setWatches: function(){
    if( this.data.watchNumAstrict === 0 ){
      request.get(`class/updateWatches/`+this.data.classId).then(res => {
        this.setData({
          watchNumAstrict : 1
        })
      },reason =>{
        console.log(reason)
      }).catch(err => {
        console.log(err)
      })
    }
    // Rload calss Information
    this.loadData()    
  },
  
  favoriteSummaryInformation: function(params){
    // obtian identity information
    // let app =  getApp();
    // this.setData({
    //   userId: app.userId    
    // })
    return request.post(`favorite/summary`,params).then(res =>{
      this.setData({
        totalNumOffavorite: res.data
      },reason =>{
        console.log(reason)
      })
    }).catch(err =>{
      console.log(err)
    })
  },

  /**
   * click the favorite button to trigger the event
   * add or del favorite
   */
  favoriteAction: function(){
    let params = {
      favoriteId:this.data.class.classId,
      favoriteType:this.data.favoriteType,
      userId:this.data.userId
    }
    this.favoriteSummaryInformation(params)
    if (this.data.totalNumOffavorite === 0 ){
      request.post(`favorite/add`,params).then(res =>{
        this.setData({
          totalNumOffavorite: 1
        })
      },reason => {
        console.log(reason)
      }).catch(err => {
        console.log(err)
      })
    }else{
      request.post(`favorite/del`,params).then(res => {
        this.setData({
          totalNumOffavorite: 0
        })
      },reason =>{
        console.log(reason)
      }).catch(err => {
        console.log(err)
      })
    }

    if( this.data.totalNumOffavorite === 0 ){
      wx.showToast({
        title: '取消收藏',
        icon: 'none',
        duration: 1500
      })
    }else{
      wx.showToast({
        title: '已收藏',
        icon: 'none',
        duration: 1500
      })
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      classId : options.id
    })

    this.loadData().then(() =>{
      wx.setNavigationBarTitle({
        title: this.data.class.title
      }) 
      let params = {
        favoriteId:this.data.class.classId,
        favoriteType:this.data.favoriteType,
        userId:this.data.userId
       }
      this.favoriteSummaryInformation(params)  
    })

    
  },

  onReady: function(){
    this.loadData().then(() =>{
      wx.setNavigationBarTitle({
        title: this.data.class.title
      }) 
      let params = {
        favoriteId:this.data.class.classId,
        favoriteType:this.data.favoriteType,
        userId:this.data.userId
       }
      this.favoriteSummaryInformation(params)  
    })
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