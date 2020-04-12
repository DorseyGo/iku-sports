// pages/course/classes/detail/details.js
const request  = require("../../../../utils/request");

Page({

  /**
   * 页面的初始数据
   */
  data: {
    classId : 1,
    class : {},
    watchNumAstrict : 0
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
  }
  ,
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let optionsClassId = options.classId //options.classId
    let that = this
    this.setData({
      classId : options.id
    })

    this.loadData().then(() =>{
      wx.setNavigationBarTitle({
        title: this.data.class.title
      })   
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