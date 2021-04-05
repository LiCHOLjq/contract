<template>
  <keep-alive>
    <div>
      <div class="editor"></div>
    </div>
  </keep-alive>
</template>

<script>
import Quill from 'quill'
import 'quill/dist/quill.snow.css'
const titleConfig = {
  'ql-bold': '加粗',
  'ql-italic': '斜体',
  'ql-underline': '下划线',
  'ql-strike': '删除线',
  'ql-script': '上标/下标',
  'ql-size': '字体大小',
  // 'ql-color': '',
  'ql-background': '颜色/底纹',
  'ql-header': '标题',
  'ql-indent': '缩进',
  'ql-align': '文本对齐',
  'ql-clean': '清除字体样式',


  // 'ql-font': '字体',
  'ql-code': '插入代码',

  'ql-link': '添加链接',





  'ql-blockquote': '引用',


  'ql-list': '列表',

  // 'ql-direction': '文本方向',
  'ql-code-block': '代码块',
  'ql-formula': '公式',
  'ql-image': '图片',
  'ql-video': '视频',

  'ql-upload': '文件',
  // 'ql-table': '插入表格',
  // 'ql-table-insert-row': '插入行',
  // 'ql-table-insert-column': '插入列',
  // 'ql-table-delete-row': '删除行',
  // 'ql-table-delete-column': '删除列'


}
export default {
  name: 'Editor',
  props: {
    value: Object
  },
  data() {
    return {
      quill: null,
      options: {
        theme: 'snow',
        modules: {

          toolbar: {
            container: [
              ['bold', 'italic', 'underline', 'strike'],
              [{ 'script': 'sub' }, { 'script': 'super' }],
              [{ 'size': ['small', false, 'large', 'huge'] }],
              [{ 'color': [] }, { 'background': [] }],
              [{ 'header': 1 }, { 'header': 2 }],
              [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
              [{ 'indent': '-1' }, { 'indent': '+1' }],
              [{ 'align': [] }],
              ['clean'],
              [{ 'list': 'ordered' }, { 'list': 'bullet' }],
              ['blockquote'],
              ['link', 'image'],
              // , 'code-block'
              // [{ 'direction': 'rtl' }],
              // [{ 'font': [] }],
              // , 'video'
              // [
              //   { table: 'TD' },
              //   { 'table-insert-row': 'TIR' },
              //   { 'table-insert-column': 'TIC' },
              //   { 'table-delete-row': 'TDR' },
              //   { 'table-delete-column': 'TDC' }
              // ]
            ],
            handlers: {
              // table: function (val) {
              //   this.quill.getModule('table').insertTable(2, 3)
              // },
              // 'table-insert-row': function () {
              //   this.quill.getModule('table').insertRowBelow()
              // },
              // 'table-insert-column': function () {
              //   this.quill.getModule('table').insertColumnRight()
              // },
              // 'table-delete-row': function () {
              //   this.quill.getModule('table').deleteRow()
              // },
              // 'table-delete-column': function () {
              //   this.quill.getModule('table').deleteColumn()
              // }
            }
          },

          table: true
        },
        placeholder: ''
      }
    }
  },
  methods: {
    addQuillTitle() {
      const oToolBar = document.querySelector('.ql-toolbar')
      const aButton = oToolBar.querySelectorAll('button')
      const aSelect = oToolBar.querySelectorAll('select')
      aButton.forEach(function (item) {
        if (item.className === 'ql-script') {
          item.value === 'sub' ? (item.title = '下标') : (item.title = '上标')
        } else if (item.className === 'ql-indent') {
          item.value === '+1' ? (item.title = '向右缩进') : (item.title = '向左缩进')
        } else {
          item.title = titleConfig[item.classList[0]]
        }
      })
      aSelect.forEach(function (item) {
        item.parentNode.title = titleConfig[item.classList[0]]
      })
    },
    getContentData() {
      return this.quill.getContents()
    },

    setContentData(html) {
      this.quill.root.innerHTML = html
    }

  },
  mounted() {
    const dom = this.$el.querySelector('.editor')
    this.quill = new Quill(dom, this.options)
    // this.quill.setContents(this.value)
    this.quill.on('text-change', () => {
      //   console.log(this.quill.getContents())
      //   this.$emit('contentData', this.quill.getContents())

      this.$emit('contentData', this.quill.root.innerHTML)
    })
    // this.$el.querySelector(
    //   '.ql-table-insert-row'
    // ).innerHTML = `<svg t="1591862376726" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="6306" width="18" height="200"><path fill="currentColor" d="M500.8 604.779L267.307 371.392l-45.227 45.27 278.741 278.613L779.307 416.66l-45.248-45.248z" p-id="6307"></path></svg>`
    // this.$el.querySelector(
    //   '.ql-table-insert-column'
    // ).innerHTML = `<svg t="1591862238963" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="6509" width="18" height="200"><path fill="currentColor" d="M593.450667 512.128L360.064 278.613333l45.290667-45.226666 278.613333 278.762666L405.333333 790.613333l-45.226666-45.269333z" p-id="6510"></path></svg>`
    // this.$el.querySelector(
    //   '.ql-table-delete-row'
    // ).innerHTML = `<svg t="1591862253524" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="6632" width="18" height="200"><path fill="currentColor" d="M500.8 461.909333L267.306667 695.296l-45.226667-45.269333 278.741333-278.613334L779.306667 650.026667l-45.248 45.226666z" p-id="6633"></path></svg>`
    // this.$el.querySelector(
    //   '.ql-table-delete-column'
    // ).innerHTML = `<svg t="1591862261059" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="6755" width="18" height="200"><path fill="currentColor" d="M641.28 278.613333l-45.226667-45.226666-278.634666 278.762666 278.613333 278.485334 45.248-45.269334-233.365333-233.237333z" p-id="6756"></path></svg>`
    this.addQuillTitle()
  },
  activated() {
    this.quill.setContents({})
  }
}
</script>