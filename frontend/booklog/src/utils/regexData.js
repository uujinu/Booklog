export const nameReg = /^[ㄱ-ㅎ가-힣a-zA-Z0-9]{2,10}$/;

export const emailReg = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

export const passwordReg = /^(?=.*[a-zA-Z])((?=.*\d)+(?=.*\W)).{8,15}$/;
