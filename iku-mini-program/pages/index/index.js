//index.js
//获取应用实例
const app = getApp();
const request  = require("../../utils/request");

const def_activities = [
  {
    id: 1,
    image: '../../images/basketball_2_2x.png'
  },
  {
    id: 2,
    image: '../../images/tennies_2_2x.png'
  }
]

const def_categories = [
  {
    id: 1,
    avatar: '../../images/home.png',
    displayName: '篮球'
  },
  {
    id: 2,
    avatar: '../../images/my.png',
    displayName: '网球'
  }
]

Page({
  data: {
    activities: [],
    categories: [],
    current: 0
  },

  onLoad: function () {
    /** request to fetch activities */
    request.get(`activities`).then(res => {
      this.setData({
        activities: res.data,
        current: 0
      })
    })

    if (this.data.activities.length == 0) {
      this.setData({
        activities: def_activities
      })
    }

    request.get(`categories`).then(res => {
      this.setData({
        categories: res.data
      })
    })

    if (this.data.categories.length == 0) {
      this.setData({
        categories: def_categories
      })
    }
  }
})
