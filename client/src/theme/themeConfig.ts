export const colorPrimary = '#224D66' // Xanh navy sang trọng, hiện đại

const themeConfig = {
  token: {
    colorPrimary // Áp dụng xanh navy làm màu chính
  },
  components: {
    Pagination: {
      itemActiveBg: colorPrimary // Nút phân trang active đổi sang xanh navy
    },
    Button: {
      colorPrimary, // Nút chính dùng xanh navy
      colorPrimaryHover: '#1B3F53' // Hover nút đậm hơn chút
    },
    Table: {
      headerBg: '#D1E2EC', // Nền header bảng xanh be nhẹ
      rowHoverBg: '#F4F7F9' // Hover dòng bảng nền xám nhẹ
    }
  }
}

export default themeConfig
