// pages/course/classes/detail/details.js
const request  = require("../../../../utils/request");

Page({

  /**
   * 页面的初始数据
   */
  data: {
    classId : 1,
    class : {},
    promotions: {}
  },
  
  loadData: function(){
    let classId = this.data.classId
    request.get(`classes/`+classId).then(res => {
      this.setData({
        class : res.data
      })

      wx.setNavigationBarTitle({
        title: res.data.classTitle
      });
        
    }, reason =>{
      console.log(reason)
    }).catch(err =>{
      console.log(err)
    })
  },

  loadPromotions: () => {
    let classId = this.data.classId
    request.get(`classes/` + classId + `/promotions`).then(res => {
      this.setData({
        promotions: res.data
      })
    }, reason => {

    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      classId : options.classId
    })

    this.loadData()
    this.loadPromotions()
  },

  onReady: function(){
    if (this.data.class) {
      wx.setNavigationBarTitle({
        title: this.data.class.classTitle
      })
    }
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