// pages/course/classes/detail/details.js
const request  = require("../../../../utils/request");
Page({

  /**
   * 页面的初始数据
   */
  data: {
    classId : 1,
    class : {},
    promotions: {},
    favorite: false,
    favoriteType: 2,
    userId: "e9b6ea6f672086252a83a48be2198d63"
  },
  
  loadData: function(){
    let classId = this.data.classId
    request.get(`classes/`+classId).then(res => {
      this.setData({
        class : res.data
      })
      
      wx.setNavigationBarTitle({
        title: res.data.classTitle
      })
    }, reason =>{
      console.log(reason)
    }).catch(err =>{
      console.log(err)
    })
  },

  checkFavoriteExistence: function() {
    request.get(`favorite`, {
      token: this.data.userId,
      favoriteId: this.data.classId,
      favoriteType: this.data.favoriteType
    }).then(res => {
      this.setData({
        favorite: res.data
      })
    })
  },

  loadPromotions: function() {
    let classId = this.data.classId
    request.get(`classes/` + classId + `/promotions`).then(res => {
      this.setData({
        promotions: res.data
      })
    })
  },

  /**
   * add favorite
   */
  addFavorite: function() {
    let userId = this.data.userId;
    request.post(`favorite`, {
      token: userId,
      favoriteId: this.data.classId,
      favoriteType: 2
    }).then((res) => {
      wx.showToast({
        title: '收藏成功',
        icon: 'success'
      })

      this.setData({
        favorite: true
      })
    })
  },

  incrWatches: function() {
    let classId = this.data.classId
    request.post(`classes/${classId}`).then(res => {
      // empty
    })
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
    this.checkFavoriteExistence();
  },

  onReady: function(){
    if (this.data.class.classTitle) {
      let title = this.data.class.classTitle
      wx.setNavigationBarTitle({
        title: title
      })
    }
  }
})