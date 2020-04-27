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
    favorite: false
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

  loadPromotions: function() {
    let classId = this.data.classId
    request.get(`classes/` + classId + `/promotions`).then(res => {
      this.setData({
        promotions: res.data
      })
    }, reason => {

    });
  },

  /**
   * add favorite
   */
  addFavorite: function() {
    request.post(`favorite`, {
      token: wx.getStorageInfoSync("token"),
      favoriteId: this.data.classId,
      favoriteType: 2
    }).then((res) => {
      wx.showToast({
        title: '收藏成功',
        icon: 'success'
      });
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