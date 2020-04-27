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
    userId: "e9b6ea6f672086252a83a48be2198d63"
  },

  /**
   * get coash information
   */
  getCoach : function() {
    request.get(`coaches/`+ this.data.coachId).then(res =>{
      this.setData({
        coach: res.data
      })
    })
  },

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
  },

  addFavorite: function() {
    let userId = this.data.userId
    request.post(`favorite`, {
      token: userId,
      favoriteType: this.data.favoriteType,
      favoriteId: this.data.coachId
    }).then(res => {
      wx.showToast({
        title: '关注成功',
        icon: 'success'
      });
    })
  },

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

  }
})