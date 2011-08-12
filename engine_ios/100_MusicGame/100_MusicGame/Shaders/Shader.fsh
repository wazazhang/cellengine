//
//  Shader.fsh
//  100_MusicGame
//
//  Created by wazazhang on 11-8-4.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

varying lowp vec4 colorVarying;

void main()
{
    gl_FragColor = colorVarying;
}
