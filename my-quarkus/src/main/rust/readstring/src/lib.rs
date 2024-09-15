#![allow(unused)]
use jni::objects::*;
use jni::JNIEnv;
use jni::sys::*;

use memmap::*;

use std::{str, path::Path, error::Error, fs::File, io::Read};

#[no_mangle]
pub unsafe extern "C" fn Java_org_acme_io_Files_readString<'local>(
    mut env: JNIEnv<'local>,
    _class: JClass<'local>,
    path: JObject<'local>,
) -> JString<'local> {
    #[inline]
    fn inner<'local>(
        env: &mut JNIEnv<'local>,
        path: JObject<'local>,
    ) -> Result<JString<'local>, Box<dyn Error>> {
        // CALL Path.toString() -> *mut jobject AS JString
        let path: JString = unsafe { JString::from_raw(
            env.call_method(path, "toString", "()Ljava/lang/String;", &[])?.l()?.into_raw()
        )};
        let path: String = env.get_string(&path)?.into();
        let f = File::open(path)?;
        let result = str::from_utf8(unsafe { &Mmap::map(&f)? }).unwrap().to_string();
        Ok(env.new_string(result).unwrap().into())
    }

    if let Ok(result) = inner(&mut env, path) {
        result
    } else {
        env.throw_new("java/io/IOException", "Failed!");
        env.new_string("").unwrap()
    }
}
