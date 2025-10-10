export interface DecodedToken {
  userId: string
  userCode: string
  fullName: string
  rolesName: string
  rolesCode: string[]
  exp: number
  email: string
  pictureUrl: string
  roleSwitch: string
  roleScreen: string | undefined
  idFacility: string | null
}


export interface UserInformation {
  email: string
  role: string
}

export interface DecodedToken {
  sub: string
  auth: string
  iat: number
  exp: number
}

